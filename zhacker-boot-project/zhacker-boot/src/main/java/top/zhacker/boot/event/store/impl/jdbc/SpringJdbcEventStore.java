package top.zhacker.boot.event.store.impl.jdbc;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import top.zhacker.boot.event.store.EventStore;
import top.zhacker.boot.event.store.StoredEvent;
import top.zhacker.boot.json.Json;
import top.zhacker.core.model.DomainEvent;


/**
 * Created by zhacker.
 * Time 2018/7/4 上午8:51
 */
public class SpringJdbcEventStore implements EventStore {

  @Autowired
  @Setter
  private JdbcTemplate jdbcTemplate;

  @Autowired
  @Setter
  private Json json;

  @Override
  public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId) {
    return jdbcTemplate.query(
            "select type_name,occurred_on,event_body,event_id " +
                "from tbl_stored_event " +
                "where event_id between ? and ? " +
                "order by event_id",
        (rs,num)-> parseResultSet(rs),
        aLowStoredEventId, aHighStoredEventId
    );
  }
  
  private StoredEvent parseResultSet(ResultSet rs) throws SQLException {
    StoredEvent storedEvent = new StoredEvent(
        rs.getString("type_name"),
        rs.getDate("occurred_on"),
        rs.getString("event_body"),
        rs.getLong("event_id")
    );
    return storedEvent;
  }
  
  @Override
  public List<StoredEvent> allStoredEventsSince(long aStoredEventId) {
    return jdbcTemplate.query(
        "select type_name,occurred_on,event_body,event_id " +
            "from tbl_stored_event " +
            "where event_id > ?  " +
            "order by event_id",
        (rs,num)-> parseResultSet(rs),
        aStoredEventId
    );
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
    
    jdbcTemplate.update(
        "insert into tbl_stored_event" +
            "(type_name, occurred_on, event_body) " +
            "value (?, ?, ?);",
        storedEvent.typeName(),
        storedEvent.occurredOn(),
        storedEvent.eventBody()
    );
    
    return storedEvent;
  }
  
  
  @Override
  public void close() {
  
  }
  
  
  @Override
  public long countStoredEvents() {
    return jdbcTemplate.queryForObject("select count(*) from tbl_stored_event", Long.class);
  }
}
