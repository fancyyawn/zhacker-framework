package top.zhacker.boot.event.store.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.event.store.EventStore;
import top.zhacker.boot.json.Json;
import top.zhacker.boot.registry.DomainRegistry;
import top.zhacker.core.model.DomainEvent;


/**
 * Created by zhacker.
 * Time 2018/6/24 上午10:11
 */
@Slf4j
public class DomainEventStoreListener {
  
  @Autowired
  @Setter
  private EventStore eventStore;
  
  @EventListener
  public void storeEvents(DomainEvent domainEvent){
    log.info("store event: {} = {}", domainEvent.getClass().getSimpleName(), DomainRegistry.service(Json.class).toJson(domainEvent));
    eventStore.append(domainEvent);
  }
}
