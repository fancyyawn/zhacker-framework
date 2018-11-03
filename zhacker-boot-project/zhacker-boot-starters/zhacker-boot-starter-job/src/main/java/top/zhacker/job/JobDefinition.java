package top.zhacker.job;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 上午11:12
 */
@Data
@Accessors(chain = true)
public class JobDefinition {
  private String name;
  private String cron;
  private Integer shards = 1;
  private String params = "";
  private Boolean failover = true;
  private Boolean overwrite = true;
  private Boolean disabled = false;
}
