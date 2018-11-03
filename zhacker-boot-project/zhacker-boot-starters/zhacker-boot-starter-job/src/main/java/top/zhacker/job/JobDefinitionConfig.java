package top.zhacker.job;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


/**
 * Created by zhacker.
 * Time 2018/1/19 下午3:32
 */
@ConfigurationProperties(prefix = "job")
public class JobDefinitionConfig {
  
  private Map<String, JobDefinition> definitions;
  
}
