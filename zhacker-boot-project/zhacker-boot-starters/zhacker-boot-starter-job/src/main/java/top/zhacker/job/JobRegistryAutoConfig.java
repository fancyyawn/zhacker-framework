package top.zhacker.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * Created by zhacker.
 * Time 2018/1/15 下午1:39
 */
@Configuration
public class JobRegistryAutoConfig {
  
  @Value("${spring.application.name}")
  private String applicationName;
  
  @Bean
  @ConditionalOnMissingBean
  public JobRegistryProperties dailyRegistryProperties(){
    return new JobRegistryProperties()
        .setNamespace("job-"+applicationName)
        .setServerList("data.host:2181");
  }
  
  
  @Configuration
  @ConditionalOnProperty(name = "job.registry")
  @EnableConfigurationProperties(JobRegistryProperties.class)
  public static class RetailJobRegistryManualConfig{
    
  }
  
}
