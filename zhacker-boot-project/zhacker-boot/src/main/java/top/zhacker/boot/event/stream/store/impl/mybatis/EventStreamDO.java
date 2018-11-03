package top.zhacker.boot.event.stream.store.impl.mybatis;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/7/29 上午8:25
 */
@Data
@Accessors(chain = true)
public class EventStreamDO {
  private Long eventId;
  private String eventBody;
  private String eventType;
  private String streamName;
  private Integer streamVersion;
}
