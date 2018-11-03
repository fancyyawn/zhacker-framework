package top.zhacker.boot.event.stream.dispatch.mybatis;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/26 上午10:10
 */
@Data
@Accessors(chain = true)
public class DispatcherLastEvent {
  
  private Long eventId;
  
}
