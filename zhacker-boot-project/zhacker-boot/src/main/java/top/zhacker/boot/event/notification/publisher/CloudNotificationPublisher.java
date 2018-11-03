package top.zhacker.boot.event.notification.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import top.zhacker.boot.event.notification.Notification;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTracker;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTrackerStore;
import top.zhacker.boot.event.store.EventStore;
import top.zhacker.boot.event.store.StoredEvent;
import top.zhacker.boot.json.Json;
import top.zhacker.core.model.DomainEvent;


/**
 * Created by zhacker.
 * Time 2018/6/24 下午3:41
 */
@EnableBinding
public class CloudNotificationPublisher implements NotificationPublisher {
  
  
  @Autowired
  @Setter
  private EventStore eventStore;
  
  @Autowired
  @Setter
  private Json json;
  
  @Autowired
  @Setter
  private PublishedNotificationTrackerStore publishedNotificationTrackerStore;
  
  @Autowired
  @Setter
  private BinderAwareChannelResolver resolver;
  
  @Value("${spring.application.name:}")
  private String applicationName;
  
  @Override
  public void publishNotifications() {
    PublishedNotificationTracker publishedNotificationTracker =
        this.publishedNotificationTrackerStore.publishedNotificationTracker();
    
    List<Notification> notifications =
        this.listUnpublishedNotifications(
            publishedNotificationTracker.mostRecentPublishedNotificationId());
    
    try {
      for (Notification notification : notifications) {
        this.publish(notification);
      }
      
      this.publishedNotificationTrackerStore
          .trackMostRecentPublishedNotification(
              publishedNotificationTracker,
              notifications);
    } catch (Exception e) {
      System.out.println("SLOTH: NotificationPublisher problem: " + e.getMessage());
    }
  }
  
  @Override
  public boolean internalOnlyTestConfirmation() {
    throw new UnsupportedOperationException("Not supported by production implementation.");
  }
  
  private EventStore eventStore() {
    return this.eventStore;
  }
  
  
  
  private List<Notification> listUnpublishedNotifications(
      long aMostRecentPublishedMessageId) {
    List<StoredEvent> storedEvents =
        this.eventStore().allStoredEventsSince(aMostRecentPublishedMessageId);
    
    List<Notification> notifications =
        this.notificationsFrom(storedEvents);
    
    return notifications;
  }
  
  private List<Notification> notificationsFrom(List<StoredEvent> aStoredEvents) {
    List<Notification> notifications =
        new ArrayList<Notification>(aStoredEvents.size());
    
    for (StoredEvent storedEvent : aStoredEvents) {
      DomainEvent domainEvent = storedEvent.toDomainEvent();
      
      Notification notification =
          new Notification(storedEvent.eventId(), domainEvent);
      
      notifications.add(notification);
    }
    
    return notifications;
  }
  
  private String topic(){
    return "topic."+applicationName + ".notification";
  }
  
  private void publish(Notification aNotification) {
    
    String notification =
       json.toJson(aNotification);
    
    String event = json.toJson(aNotification.getEvent());
    
    resolver.resolveDestination(aNotification.typeName()).send(MessageBuilder.withPayload(event).build());
    resolver.resolveDestination(topic()).send(MessageBuilder.withPayload(notification).build());
  }
}
