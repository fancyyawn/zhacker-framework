package top.zhacker.job;

import com.google.common.base.Strings;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.val;


/**
 * Created by zhacker.
 * Time 2017/12/4 上午9:33
 */
@Configuration
@Import(JobRegistryAutoConfig.class)
public class JobAutoConfig {
  
  @Autowired(required = false)
  private List<SimpleJob> simpleJobs = new ArrayList<>();
  
//  @Value("${retail.jobs}")
//  private String jobDefinitions;
  
  @Autowired(required = false)
  private List<JobDefinition> definitions = new ArrayList<>();
  
  @Bean(initMethod = "init")
  @ConditionalOnMissingBean
  public ZookeeperRegistryCenter zookeeperRegistryCenter(JobRegistryProperties properties) {
    return new ZookeeperRegistryCenter(new ZookeeperConfiguration(properties.getServerList(), properties.getNamespace()));
  }
  
  @Bean
  public List<JobScheduler> jobSchedulers(ZookeeperRegistryCenter zookeeperRegistryCenter) {
    
    List<JobScheduler> schedulers = new ArrayList<>();
    
//    List<RetailJobDefinition> definitions = JSON.parseArray(jobDefinitions, RetailJobDefinition.class);
    
    for(SimpleJob job : simpleJobs){
      
      JobDefinition config = definitions.stream()
          .filter(x-> Objects.equals(x.getName(), getJobName(job)))
          .findAny()
          .orElseThrow(()-> new RuntimeException("job定义缺失"));
      
      LiteJobConfiguration liteJobConfiguration = getLiteJobConfiguration(job.getClass(), config);
      schedulers.add(new SpringJobScheduler(job, zookeeperRegistryCenter, liteJobConfiguration));
    }
    
    for(JobScheduler scheduler : schedulers){
      scheduler.init();
    }
    return schedulers;
  }
  
  private String getJobName(SimpleJob job){
    
    Class<?> jobClass = job.getClass();
    if(jobClass.getSimpleName().contains("Enhance")){
      jobClass = jobClass.getSuperclass();
    }
    
    String jobName = jobClass.getSimpleName();
    Job jobAnnotation = jobClass.getAnnotation(Job.class);
    
    if(jobAnnotation!=null && !Strings.isNullOrEmpty(jobAnnotation.name())){
      jobName = jobAnnotation.name();
    }
    return jobName;
  }
  
  private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, JobDefinition config) {
    
    val jobCore = JobCoreConfiguration.newBuilder(jobClass.getName(), config.getCron(), config.getShards())
        .shardingItemParameters(config.getParams())
        .failover(config.getFailover())
        .build();
    
    val jobWithName = new SimpleJobConfiguration(jobCore, jobClass.getCanonicalName());
  
    return LiteJobConfiguration.newBuilder(jobWithName)
        .overwrite(config.getOverwrite())
        .disabled(config.getDisabled())
        .build();
  }
  
}
