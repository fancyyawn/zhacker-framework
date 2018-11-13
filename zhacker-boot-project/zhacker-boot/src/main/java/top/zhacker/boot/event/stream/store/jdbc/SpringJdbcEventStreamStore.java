package top.zhacker.boot.event.stream.store.jdbc;

import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhacker.boot.event.stream.*;
import top.zhacker.boot.event.stream.store.EventStreamStore;
import top.zhacker.boot.json.Json;
import top.zhacker.core.model.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringJdbcEventStreamStore implements EventStreamStore {

    @Autowired
    @Setter
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Setter
    private Json json;

    private EventNotifiable eventNotifiable;

    @Override
    public void appendWith(EventStreamId aStartingIdentity, List<DomainEvent> anEvents) {

        int index = 0;

        for (DomainEvent event : anEvents) {
            this.appendEventStore(aStartingIdentity, index++, event);
        }

        this.notifyDispatchableEvents();
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public List<DispatchableDomainEvent> eventsSince(long aLastReceivedEvent) {

        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT event_id, event_body, event_type FROM tbl_es_event_store "
                + "WHERE event_id > ? "
                + "ORDER BY event_id", aLastReceivedEvent);


        List<DispatchableDomainEvent> sequence = this.buildEventSequence(result);

        return sequence;
    }

    @Override
    public EventStream eventStreamSince(EventStreamId anIdentity) {

        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT stream_version, event_type, event_body FROM tbl_es_event_store "
                                + "WHERE stream_name = ? AND stream_version >= ? "
                                + "ORDER BY stream_version",
               anIdentity.streamName(), anIdentity.streamVersion());

            EventStream eventStream = this.buildEventStream(result);

            if (eventStream.version() == 0) {
                throw new EventStreamStoreException(
                        "There is no such event stream: "
                        + anIdentity.streamName()
                        + " : "
                        + anIdentity.streamVersion());
            }


            return eventStream;
    }

    @Override
    public EventStream fullEventStreamFor(EventStreamId anIdentity) {

        val result = jdbcTemplate.queryForList("SELECT stream_version, event_type, event_body FROM tbl_es_event_store "
                                + "WHERE stream_name = ? "
                                + "ORDER BY stream_version", anIdentity.streamName());

        return this.buildEventStream(result);
    }

    @Override
    public void purge() {
        jdbcTemplate.update("delete from tbl_es_event_store");
    }

    @Override
    public void registerEventNotifiable(EventNotifiable anEventNotifiable) {
        this.eventNotifiable = anEventNotifiable;
    }

    private void appendEventStore(
            EventStreamId anIdentity,
            int anIndex,
            DomainEvent aDomainEvent){

        jdbcTemplate.update( "INSERT INTO tbl_es_event_store VALUES(?, ?, ?, ?, ?)",
                0,
                json.toJson(aDomainEvent),
                aDomainEvent.getClass().getName(),
                anIdentity.streamName(),
                anIdentity.streamVersion() + anIndex
        );
    }

    @SuppressWarnings("unchecked")
    private List<DispatchableDomainEvent> buildEventSequence(List<Map<String, Object>> aResultSet) {

        List<DispatchableDomainEvent> events = new ArrayList<DispatchableDomainEvent>();

        for(val i : aResultSet){
            long eventId = (Long)i.get("event_id");

            String eventClassName = (String)i.get("event_type");

            String eventBody = (String)i.get("event_body");

            Class<DomainEvent> eventClass = null;
            try {
                eventClass = (Class<DomainEvent>) Class.forName(eventClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            DomainEvent domainEvent = json.fromJson(eventBody, eventClass);

            events.add(new DispatchableDomainEvent(eventId, domainEvent));
        }

        return events;
    }

    @SuppressWarnings("unchecked")
    private EventStream buildEventStream(List<Map<String, Object>> aResultSet) {

        List<DomainEvent> events = new ArrayList<DomainEvent>();

        int version = 0;

        for(val i : aResultSet){
            version = (Integer)i.get("stream_version");

            String eventClassName = (String)i.get("event_type");

            String eventBody = (String)i.get("event_body");

            Class<DomainEvent> eventClass = null;
            try {
                eventClass = (Class<DomainEvent>) Class.forName(eventClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            DomainEvent domainEvent = json.fromJson(eventBody, eventClass);

            events.add(domainEvent);
        }

        return new DefaultEventStream(events, version);
    }


    private EventNotifiable eventNotifiable() {
        return this.eventNotifiable;
    }

    private void notifyDispatchableEvents() {
        EventNotifiable eventNotifiable = this.eventNotifiable();

        if (eventNotifiable != null) {
            this.eventNotifiable().notifyDispatchableEvents();
        }
    }
}