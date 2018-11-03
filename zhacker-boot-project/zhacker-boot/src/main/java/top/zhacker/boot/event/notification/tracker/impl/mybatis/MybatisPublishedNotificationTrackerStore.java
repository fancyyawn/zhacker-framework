package top.zhacker.boot.event.notification.tracker.impl.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import top.zhacker.boot.event.notification.Notification;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTracker;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTrackerStore;


/**
 * Created by zhacker.
 * Time 2018/7/28 下午5:18
 */
@Repository
public class MybatisPublishedNotificationTrackerStore implements PublishedNotificationTrackerStore {
  
  @Value("${spring.application.name: default}")
  private String typeName;
  
  @Autowired
  private PublishedNotificationTrackerStoreMapper mapper;
  
  @Override
  public PublishedNotificationTracker publishedNotificationTracker() {
    return this.publishedNotificationTracker(typeName);
  }
  
  
  @Override
  public PublishedNotificationTracker publishedNotificationTracker(String aTypeName) {
     return Optional.ofNullable(mapper.findByTypeName(aTypeName))
         .orElse(new PublishedNotificationTracker(aTypeName));
  }
  
  @Override
  public void trackMostRecentPublishedNotification(PublishedNotificationTracker aPublishedNotificationTracker, List<Notification> aNotifications) {
    
    int lastIndex = aNotifications.size() - 1;
  
    if (lastIndex >= 0) {
      long mostRecentId = aNotifications.get(lastIndex).notificationId();
  
      aPublishedNotificationTracker.setMostRecentPublishedNotificationId(mostRecentId);
  
      if (aPublishedNotificationTracker.publishedNotificationTrackerId() == -1) {
        mapper.create(aPublishedNotificationTracker);
      }else {
        mapper.updateMostRecentPublished(aPublishedNotificationTracker.publishedNotificationTrackerId(), mostRecentId);
      }
    }
  }
  
  
  @Override
  public String typeName() {
    return typeName;
  }
}
