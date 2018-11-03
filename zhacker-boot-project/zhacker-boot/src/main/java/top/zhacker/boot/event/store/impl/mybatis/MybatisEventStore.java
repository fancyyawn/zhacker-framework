package top.zhacker.boot.event.store.impl.mybatis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import top.zhacker.boot.event.store.EventStore;
import top.zhacker.boot.event.store.StoredEvent;
import top.zhacker.boot.json.Json;
import top.zhacker.core.model.DomainEvent;


/**
 * Created by zhacker.
 * Time 2018/7/28 下午5:05
 */
public class MybatisEventStore implements EventStore {
  
  @Autowired
  private EventStoreMapper eventStoreMapper;
  
  @Autowired
  private Json json;
  
  @Override
  public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId) {
    return eventStoreMapper.allStoredEventsBetween(aLowStoredEventId,aHighStoredEventId);
  }
  
  @Override
  public List<StoredEvent> allStoredEventsSince(long aStoredEventId) {
    return eventStoreMapper.allStoredEventsSince(aStoredEventId);
  }
  
  @Override
  public StoredEvent append(DomainEvent aDomainEvent) {
    String eventSerialization =
        json.toJson(aDomainEvent);
  
    StoredEvent storedEvent =
        new StoredEvent(
            aDomainEvent.getClass().getName(),
            aDomainEvent.getOccurredOn(),
            eventSerialization);
    
    eventStoreMapper.create(storedEvent);
    
    return storedEvent;
  }
  
  
  @Override
  public void close() {
  
  }
  
  @Override
  public long countStoredEvents() {
    return eventStoreMapper.countStoredEvents();
  }
}
