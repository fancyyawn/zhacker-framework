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

package top.zhacker.boot.event.stream.dispatch.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.boot.event.stream.DispatchableDomainEvent;
import top.zhacker.boot.event.stream.EventNotifiable;
import top.zhacker.boot.event.stream.dispatch.EventStreamDispatcher;
import top.zhacker.boot.event.stream.dispatch.sql.ConnectionProvider;
import top.zhacker.boot.event.stream.store.EventStreamStore;

import java.util.ArrayList;
import java.util.List;


//@Component("parentEventStreamDispatcher")
public class SpringJdbcParentEventStreamDispatcher implements EventStreamDispatcher, EventNotifiable {

    private JdbcTemplate jdbcTemplate;
    private long lastDispatchedEventId;
    private List<EventStreamDispatcher> registeredDispatchers;
    private EventStreamStore eventStreamStore;

    @Autowired
    public SpringJdbcParentEventStreamDispatcher(JdbcTemplate jdbcTemplate, EventStreamStore eventStreamStore) {
        super();

        this.jdbcTemplate = jdbcTemplate;
        this.setRegisteredDispatchers(new ArrayList<EventStreamDispatcher>());

        this.eventStreamStore = eventStreamStore;
        eventStreamStore.registerEventNotifiable(this);

        this.setLastDispatchedEventId(this.queryLastDispatchedEventId());

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

        try {
            List<DispatchableDomainEvent> undispatchedEvents =
                    eventStreamStore
                        .eventsSince(this.lastDispatchedEventId());

            if (!undispatchedEvents.isEmpty()) {

                for (DispatchableDomainEvent event : undispatchedEvents) {
                    this.dispatch(event);
                }

                DispatchableDomainEvent withLastEventId =
                        undispatchedEvents.get(undispatchedEvents.size() - 1);

                long lastDispatchedEventId = withLastEventId.eventId();

                this.setLastDispatchedEventId(lastDispatchedEventId);

                this.saveLastDispatchedEventId(lastDispatchedEventId);
            }

//            connection.commit();

        } catch (Throwable t) {
            throw new IllegalStateException("Cannot dispatch events because: " + t.getMessage(), t);
        } finally {
            ConnectionProvider.closeConnection();
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

        long lastHandledEventId = 0;

        Long maxEventId = jdbcTemplate.queryForObject("select max(event_id) from tbl_dispatcher_last_event", Long.class);
        if(maxEventId!=null){
            lastHandledEventId = maxEventId;
        }else{
            this.saveLastDispatchedEventId(0);
        }
        return lastHandledEventId;
    }

    private void saveLastDispatchedEventId(long aLastDispatchedEventId) {

        int updated = jdbcTemplate.update("update tbl_dispatcher_last_event set event_id=?", aLastDispatchedEventId);

        if (updated == 0) {
            jdbcTemplate.update("insert into tbl_dispatcher_last_event values(?)", aLastDispatchedEventId);
        }
    }

    private List<EventStreamDispatcher> registeredDispatchers() {
        return this.registeredDispatchers;
    }

    private void setRegisteredDispatchers(List<EventStreamDispatcher> aDispatchers) {
        this.registeredDispatchers = aDispatchers;
    }
}
