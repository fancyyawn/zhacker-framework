package top.zhacker.boot.event.publish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Created by zhacker.
 * Time 2018/6/24 上午10:14
 */
@Slf4j
public class DomainEventPublisher implements ApplicationContextAware {
  
  private static ApplicationContext applicationContext;
  
  public static <T> void publish(final T aDomainEvent){
    if(applicationContext!=null) {
      applicationContext.publishEvent(aDomainEvent);
    }else{
      log.warn("publish domain event fail because ApplicationContext null, event={}", aDomainEvent);
    }
  }
  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    DomainEventPublisher.applicationContext = applicationContext;
  }
}
