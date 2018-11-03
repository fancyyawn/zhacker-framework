package top.zhacker.job;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2017/12/4 下午12:52
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "job.registry")
public class JobRegistryProperties {
  private String serverList;
  private String namespace;
}
