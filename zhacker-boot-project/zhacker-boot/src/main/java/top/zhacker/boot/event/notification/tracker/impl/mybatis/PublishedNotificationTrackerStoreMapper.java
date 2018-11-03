package top.zhacker.boot.event.notification.tracker.impl.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.zhacker.boot.event.notification.tracker.PublishedNotificationTracker;


/**
 * Created by zhacker.
 * Time 2018/7/28 下午5:10
 */
@Mapper
public interface PublishedNotificationTrackerStoreMapper {
  
  @Select("select * from tbl_published_notification_tracker where type_name = #{typeName} order by published_notification_tracker_id asc limit 1")
  PublishedNotificationTracker findByTypeName(@Param("typeName") String typeName);
  
  @Options(useGeneratedKeys = true, keyProperty = "publishedNotificationTrackerId")
  @Insert("INSERT INTO `tbl_published_notification_tracker` " +
      "(`most_recent_published_notification_id`, `type_name`, `concurrency_version`) " +
      "VALUES (#{mostRecentPublishedNotificationId}, #{typeName}, #{concurrencyVersion})")
  void create(PublishedNotificationTracker tracker);
  
  @Update("update `tbl_published_notification_tracker` " +
      "set `most_recent_published_notification_id`= #{mostPublishedId},  `concurrency_version`= concurrency_version + 1 " +
      "where published_notification_tracker_id = #{trackerId}")
  int updateMostRecentPublished(@Param("trackerId") Long trackerId, @Param("mostPublishedId") Long mostPublishedId);
}
