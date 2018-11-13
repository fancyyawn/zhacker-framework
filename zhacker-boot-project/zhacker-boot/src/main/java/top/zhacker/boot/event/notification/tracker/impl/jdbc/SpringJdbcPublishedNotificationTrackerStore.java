package top.zhacker.boot.event.notification.tracker.impl.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhacker.boot.event.notification.Notification;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTracker;
import top.zhacker.boot.event.notification.tracker.PublishedNotificationTrackerStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/7/1 下午1:35
 */
@Slf4j
public class SpringJdbcPublishedNotificationTrackerStore implements PublishedNotificationTrackerStore {
  
  @Value("${spring.application.name: default}")
  private String typeName;
  
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  
  @Override
  public PublishedNotificationTracker publishedNotificationTracker() {
    return this.publishedNotificationTracker(this.typeName());
  }
  
  @Override
  public PublishedNotificationTracker publishedNotificationTracker(String aTypeName) {
    
    PublishedNotificationTracker publishedNotificationTracker = null;
    try {
      publishedNotificationTracker = jdbcTemplate.queryForObject(
          "select * from tbl_published_notification_tracker where type_name = ? order by published_notification_tracker_id asc limit 1",
          (rs, rowNum)-> parseResultSet(rs),
          this.typeName()
      );
    } catch (Exception e) {
      // fall through
      log.warn("query PublishedNotificationTracker fail, typeName={}", aTypeName, e);
    }
    
    if (publishedNotificationTracker == null) {
      publishedNotificationTracker = new PublishedNotificationTracker(this.typeName());
    }
    return publishedNotificationTracker;
  }
  
  private PublishedNotificationTracker parseResultSet(ResultSet rs) throws SQLException {
    PublishedNotificationTracker tracker = new PublishedNotificationTracker(rs.getString("type_name"));
    tracker.setMostRecentPublishedNotificationId(rs.getLong("most_recent_published_notification_id"));
    tracker.setPublishedNotificationTrackerId(rs.getLong("published_notification_tracker_id"));
    tracker.setConcurrencyVersion(rs.getInt("concurrency_version"));
    return tracker;
  }
  
  @Override
  public void trackMostRecentPublishedNotification(
      PublishedNotificationTracker aPublishedNotificationTracker,
      List<Notification> aNotifications) {
    int lastIndex = aNotifications.size() - 1;
    
    if (lastIndex >= 0) {
      long mostRecentId = aNotifications.get(lastIndex).notificationId();
      
      aPublishedNotificationTracker.setMostRecentPublishedNotificationId(mostRecentId);
      
      if(aPublishedNotificationTracker.publishedNotificationTrackerId() == -1){
        jdbcTemplate.update(
            "INSERT INTO `tbl_published_notification_tracker` " +
                "(`most_recent_published_notification_id`, `type_name`, `concurrency_version`) " +
                "VALUES " + " (?, ?, ?);" ,
            aPublishedNotificationTracker.mostRecentPublishedNotificationId(),
            aPublishedNotificationTracker.typeName(),
            aPublishedNotificationTracker.concurrencyVersion()
        );
      }else{
        jdbcTemplate.update(
            "update `tbl_published_notification_tracker` " +
                "set `most_recent_published_notification_id`=?,  `concurrency_version`= concurrency_version + 1 " +
                "where published_notification_tracker_id=?;" ,
            aPublishedNotificationTracker.mostRecentPublishedNotificationId(),
            aPublishedNotificationTracker.publishedNotificationTrackerId()
        );
      }
    }
  }
  @Override
  public String typeName() {
    return typeName;
  }
}
