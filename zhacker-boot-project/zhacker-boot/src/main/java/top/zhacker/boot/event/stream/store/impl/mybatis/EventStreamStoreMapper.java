package top.zhacker.boot.event.stream.store.impl.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhacker.
 * Time 2018/7/29 上午8:21
 */
@Mapper
public interface EventStreamStoreMapper {
  
  @Select("SELECT * FROM tbl_es_event_store "
      + "WHERE event_id > #{lastReceivedEvent} "
      + "ORDER BY event_id")
  List<EventStreamDO> eventsSince(@Param("lastReceivedEvent") long lastReceivedEvent);
  
  @Select("SELECT stream_version, event_type, event_body FROM tbl_es_event_store "
      + "WHERE stream_name = #{streamName} AND stream_version >= #{streamVersion} "
      + "ORDER BY stream_version")
  List<EventStreamDO> findByStreamNameAndVersion(@Param("streamName") String streamName, @Param("streamVersion") Integer streamVersion);
  
  @Select("SELECT stream_version, event_type, event_body FROM tbl_es_event_store "
      + "WHERE stream_name = ? "
      + "ORDER BY stream_version")
  List<EventStreamDO> findByStreamName(@Param("streamName") String streamName);
  
  @Select("SELECT max(stream_version) FROM tbl_es_event_store "
      + "WHERE stream_name = ? ")
  Integer findCurrentStreamVersion(@Param("streamName") String streamName);
  
  @Delete("delete from tbl_es_event_store")
  int deleteAll();
  
  @Insert("INSERT INTO `tbl_es_event_store` ( `event_body`, `event_type`, `stream_name`, `stream_version`)\n" +
      "VALUES (#{eventBody}, #{eventType}, #{streamName}, #{streamVersion});\n")
  void insert(EventStreamDO entity);
  
}
