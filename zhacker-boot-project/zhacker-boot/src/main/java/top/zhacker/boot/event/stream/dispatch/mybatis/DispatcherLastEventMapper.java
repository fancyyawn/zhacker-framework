package top.zhacker.boot.event.stream.dispatch.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * Created by zhacker.
 * Time 2018/8/26 上午10:09
 */
@Mapper
public interface DispatcherLastEventMapper {
  
  @Insert("insert into tbl_dispatcher_last_event values(#{eventId})")
  void insert(DispatcherLastEvent event);
  
  @Update("update tbl_dispatcher_last_event set event_id= #{eventId}")
  int update(DispatcherLastEvent event);
  
  @Select("select max(event_id) from tbl_dispatcher_last_event")
  Long findLastEventId();
}
