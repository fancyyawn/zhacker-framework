package top.zhacker.boot.starter.event.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhacker.boot.event.notification.tracker.impl.jdbc.SpringJdbcPublishedNotificationTrackerStore;
import top.zhacker.boot.event.process.impl.jdbc.SpringJdbcTimeConstrainedProcessTrackerRepository;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.event.store.impl.jdbc.SpringJdbcEventStore;
import top.zhacker.boot.event.store.listener.DomainEventStoreListener;
import top.zhacker.boot.event.stream.dispatch.jdbc.SpringJdbcParentEventStreamDispatcher;
import top.zhacker.boot.event.stream.store.EventStreamStore;
import top.zhacker.boot.event.stream.store.jdbc.SpringJdbcEventStreamStore;
import top.zhacker.boot.starter.CommonAutoConfig;


/**
 * Created by zhacker.
 * Time 2018/7/1 下午4:57
 */
@Configuration
@Import(CommonAutoConfig.class)
public class SpringJdbcEventAutoConfig {

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
  public SpringJdbcPublishedNotificationTrackerStore springJdbcPublishedNotificationTrackerStore(){
    return new SpringJdbcPublishedNotificationTrackerStore();
  }

  @Bean
  public SpringJdbcEventStore springJdbcEventStore(){
    return new SpringJdbcEventStore();
  }


  @Bean
  public SpringJdbcTimeConstrainedProcessTrackerRepository springJdbcTimeConstrainedProcessTrackerRepository(){
    return new SpringJdbcTimeConstrainedProcessTrackerRepository();
  }

  @Bean
  public SpringJdbcEventStreamStore springJdbcEventStreamStore(){
    return new SpringJdbcEventStreamStore();
  }

  @Bean
  public SpringJdbcParentEventStreamDispatcher parentEventStreamDispatcher(JdbcTemplate jdbcTemplate, EventStreamStore eventStreamStore){
    return new SpringJdbcParentEventStreamDispatcher(jdbcTemplate, eventStreamStore);
  }
}
