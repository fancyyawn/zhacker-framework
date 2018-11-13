package top.zhacker.boot.event.stream.store.mybatis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import top.zhacker.boot.event.stream.DefaultEventStream;
import top.zhacker.boot.event.stream.DispatchableDomainEvent;
import top.zhacker.boot.event.stream.EventNotifiable;
import top.zhacker.boot.event.stream.EventStream;
import top.zhacker.boot.event.stream.EventStreamId;
import top.zhacker.boot.event.stream.EventStreamStoreException;
import top.zhacker.boot.event.stream.store.EventStreamStore;
import top.zhacker.boot.json.Json;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.core.model.DomainEvent;


/**
 * Created by zhacker.
 * Time 2018/7/29 上午8:37
 */
@Slf4j
public class MybatisEventStreamStore implements EventStreamStore {
  
  @Autowired
  @Setter
  private EventStreamStoreMapper mapper;
  
  private EventNotifiable eventNotifiable;
  
  @Autowired
  @Setter
  private Json json;
  
  @Override
  public void appendWith(EventStreamId aStartingIdentity, List<DomainEvent> anEvents) {
    
    int index = 0;

    for (DomainEvent event : anEvents) {
      this.appendEventStore(aStartingIdentity, index++, event);
    }
  
    this.notifyDispatchableEvents();
  }
  
  private void appendEventStore(
      EventStreamId anIdentity,
      int anIndex,
      DomainEvent aDomainEvent) {
    
    mapper.insert(new EventStreamDO()
        .setEventId(-1L)
        .setEventType(aDomainEvent.getClass().getName())
        .setEventBody(json.toJson(aDomainEvent))
        .setStreamName(anIdentity.streamName())
        .setStreamVersion(anIdentity.streamVersion() + anIndex)
    );
  }
  
  private void notifyDispatchableEvents() {
    EventNotifiable eventNotifiable = this.eventNotifiable;
    
    if (eventNotifiable != null) {
      this.eventNotifiable.notifyDispatchableEvents();
    }
  }
  
  @Override
  public void close() {
  
  }
  
  
  @Override
  public List<DispatchableDomainEvent> eventsSince(long aLastReceivedEvent) {
    
    return mapper.eventsSince(aLastReceivedEvent)
        .stream()
        .map(event->new DispatchableDomainEvent(event.getEventId(), parseEvent(event)))
        .collect(Collectors.toList());
  }
  
  DomainEvent parseEvent(EventStreamDO event){
    Class<DomainEvent> eventClass = null;
    try {
      eventClass = (Class<DomainEvent>) Class.forName(event.getEventType());
    } catch (ClassNotFoundException e) {
      log.warn("invalid event type={}",event.getEventType(), e);
      throw new BusinessException("invalid.event.type");
    }
  
    DomainEvent domainEvent = json.fromJson(event.getEventBody(), eventClass);
    
    return domainEvent;
  }
  
  
  @Override
  public EventStream eventStreamSince(EventStreamId eventStreamId) {
  
    val streams = mapper.findByStreamNameAndVersion(eventStreamId.streamName(), eventStreamId.streamVersion());
    return buildEventStream(eventStreamId,streams);
  }
  
  
  private EventStream buildEventStream(EventStreamId eventStreamId, List<EventStreamDO> streams){
    
    List<DomainEvent> events = streams
        .stream()
        .map(this::parseEvent)
        .collect(Collectors.toList());
    
    int version = streams.stream().mapToInt(EventStreamDO::getStreamVersion).max()
        .orElseThrow(()-> new EventStreamStoreException("There is no such event stream: " + eventStreamId.streamName() + " : " + eventStreamId.streamVersion()));
    
    return new DefaultEventStream(events, version);
  }
  
  
  
  
  @Override
  public EventStream fullEventStreamFor(EventStreamId eventStreamId) {
    
    val streams =  mapper.findByStreamName(eventStreamId.streamName());
    
    return buildEventStream(eventStreamId, streams);
  }
  
  
  @Override
  public void purge() {
    mapper.deleteAll();
  }
  
  
  @Override
  public void registerEventNotifiable(EventNotifiable anEventNotifiable) {
    this.eventNotifiable = anEventNotifiable;
  }
}
