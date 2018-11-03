//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package top.zhacker.boot.event.stream.dispatch.mybatis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import lombok.Setter;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.event.stream.DispatchableDomainEvent;
import top.zhacker.boot.event.stream.EventNotifiable;
import top.zhacker.boot.event.stream.dispatch.EventStreamDispatcher;
import top.zhacker.boot.event.stream.store.EventStreamStore;


//@Component("parentEventStreamDispatcher")
public class MybatisParentEventStreamDispatcher implements EventStreamDispatcher, EventNotifiable {
    
    private long lastDispatchedEventId;
    private List<EventStreamDispatcher> registeredDispatchers = new ArrayList<>();
    
    @Setter
    @Autowired
    private EventStreamStore eventStreamStore;
    
    @Autowired
    @Setter
    private DispatcherLastEventMapper dispatcherLastEventMapper;
    
    @PostConstruct
    public void init() {
        
        eventStreamStore.registerEventNotifiable(this);

        this.setLastDispatchedEventId(this.queryLastDispatchedEventId());

//        this.notifyDispatchableEvents();
    }

    @Override
    public void dispatch(DispatchableDomainEvent aDispatchableDomainEvent) {
        DomainEventPublisher.publish(aDispatchableDomainEvent.domainEvent());

        for (EventStreamDispatcher eventDispatcher : this.registeredDispatchers()) {
           eventDispatcher.dispatch(aDispatchableDomainEvent);
        }
    }

    @Override
    public void notifyDispatchableEvents() {
        
        List<DispatchableDomainEvent> undispatchedEvents = eventStreamStore.eventsSince(this.lastDispatchedEventId());

        if (!undispatchedEvents.isEmpty()) {

            for (DispatchableDomainEvent event : undispatchedEvents) {
                this.dispatch(event);
            }

            DispatchableDomainEvent withLastEventId = undispatchedEvents.get(undispatchedEvents.size() - 1);

            long lastDispatchedEventId = withLastEventId.eventId();

            this.setLastDispatchedEventId(lastDispatchedEventId);

            this.saveLastDispatchedEventId(lastDispatchedEventId);
        }
    }

    @Override
    public void registerEventDispatcher(EventStreamDispatcher anEventDispatcher) {
        this.registeredDispatchers().add(anEventDispatcher);
    }

    @Override
    public boolean understands(DispatchableDomainEvent aDispatchableDomainEvent) {
        return true;
    }

    private long lastDispatchedEventId() {
        return this.lastDispatchedEventId;
    }

    private void setLastDispatchedEventId(long aLastDispatchedEventId) {
        this.lastDispatchedEventId = aLastDispatchedEventId;
    }

    private long queryLastDispatchedEventId() {
        return Optional.ofNullable(dispatcherLastEventMapper.findLastEventId()).orElse(0L);
    }

    private void saveLastDispatchedEventId(long aLastDispatchedEventId) {

        int updated = dispatcherLastEventMapper.update(new DispatcherLastEvent().setEventId(aLastDispatchedEventId));
        if(updated==0){
            dispatcherLastEventMapper.insert(new DispatcherLastEvent().setEventId(aLastDispatchedEventId));
        }
    }

    private List<EventStreamDispatcher> registeredDispatchers() {
        return this.registeredDispatchers;
    }

    private void setRegisteredDispatchers(List<EventStreamDispatcher> aDispatchers) {
        this.registeredDispatchers = aDispatchers;
    }
}
