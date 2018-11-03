package top.zhacker.boot.starter.event.mybatis;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import top.zhacker.boot.event.notification.publisher.CloudNotificationPublisher;
import top.zhacker.boot.event.notification.publisher.NotificationPublisher;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTrackerStore;
import top.zhacker.boot.event.notification.tracker.impl.mybatis.MybatisPublishedNotificationTrackerStore;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.event.store.EventStore;
import top.zhacker.boot.event.store.impl.mybatis.MybatisEventStore;
import top.zhacker.boot.event.store.listener.DomainEventStoreListener;
import top.zhacker.boot.event.stream.dispatch.mybatis.MybatisParentEventStreamDispatcher;
import top.zhacker.boot.event.stream.store.EventStreamStore;
import top.zhacker.boot.event.stream.store.impl.mybatis.MybatisEventStreamStore;
import top.zhacker.boot.starter.CommonAutoConfig;


/**
 * Created by zhacker.
 * Time 2018/8/26 下午5:43
 */
@Configuration
@Import(CommonAutoConfig.class)
public class MybatisEventAutoConfig {
  
  @Bean
  @ConditionalOnMissingBean(DomainEventPublisher.class)
  public DomainEventPublisher domainEventPublisher(){
    return new DomainEventPublisher();
  }
  
  @Bean
  @ConditionalOnMissingBean(DomainEventStoreListener.class)
  public DomainEventStoreListener domainEventStoreListener(){
    return new DomainEventStoreListener();
  }
  
  @Bean
  public ConfigurationCustomizer eventMapperConfigurationCustomizer(){
    return configuration -> {
      configuration.addMappers("top.zhacker.boot.event");
      configuration.setMapUnderscoreToCamelCase(true);
    };
  }
  
  @Configuration
  @Import(EventAutoConfiguredMapperScannerRegistrar.class)
  public static class EventMapperConfig{
  
  }
  
  @Bean
  @ConditionalOnMissingBean(EventStore.class)
  public MybatisEventStore mybatisEventStore(){
    return new MybatisEventStore();
  }
  
  @Bean
  @ConditionalOnMissingBean(EventStreamStore.class)
  public MybatisEventStreamStore mybatisEventStreamStore(){
    return new MybatisEventStreamStore();
  }
  
  @Bean
  @ConditionalOnMissingBean(name="parentEventStreamDispatcher")
  public MybatisParentEventStreamDispatcher parentEventStreamDispatcher(){
    return new MybatisParentEventStreamDispatcher();
  }
  
//  @Bean
//  @ConditionalOnBean({EventStore.class, PublishedNotificationTrackerStore.class, BinderAwareChannelResolver.class})
//  @ConditionalOnMissingBean(NotificationPublisher.class)
//  public NotificationPublisher cloudNotificationPublisher(){
//    return new CloudNotificationPublisher();
//  }
//
  @Bean
  @ConditionalOnMissingBean(PublishedNotificationTrackerStore.class)
  public PublishedNotificationTrackerStore mybatisPublishedNotificationTrackerStore(){
    return new MybatisPublishedNotificationTrackerStore();
  }
  
}
