package top.zhacker.boot.starter.event.leveldb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.zhacker.boot.event.notification.tracker.impl.leveldb.LevelDBPublishedNotificationTrackerStore;
import top.zhacker.boot.event.process.impl.leveldb.LevelDBTimeConstrainedProcessTrackerRepository;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.event.store.impl.leveldb.LevelDBEventStore;
import top.zhacker.boot.event.store.listener.DomainEventStoreListener;
import top.zhacker.boot.leveldb.LevelDbTransactionalAspect;
import top.zhacker.boot.starter.CommonAutoConfig;


/**
 * Created by zhacker.
 * Time 2018/7/1 下午4:57
 */
@Configuration
@Import(CommonAutoConfig.class)
public class LeveldbEventAutoConfig {

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
  public LevelDbTransactionalAspect levelDbTransactionalAspect(){
    return new LevelDbTransactionalAspect();
  }

  @Bean
  public LevelDBPublishedNotificationTrackerStore levelDBPublishedNotificationTrackerStore(@Value("${spring.application.name: default}") String typeName){
    return new LevelDBPublishedNotificationTrackerStore(typeName);
  }

  @Bean
  public LevelDBTimeConstrainedProcessTrackerRepository levelDBTimeConstrainedProcessTrackerRepository(){
    return new LevelDBTimeConstrainedProcessTrackerRepository();
  }

  @Bean
  public LevelDBEventStore levelDBEventStore(){
    return new LevelDBEventStore();
  }
}
