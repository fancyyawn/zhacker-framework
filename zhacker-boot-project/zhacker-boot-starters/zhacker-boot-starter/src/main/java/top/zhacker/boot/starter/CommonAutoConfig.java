package top.zhacker.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.zhacker.boot.aop.exception.CommonExceptionAspect;
import top.zhacker.boot.aop.log.ParamLogAspect;
import top.zhacker.boot.idgen.CommonSelfIdGenerator;
import top.zhacker.boot.idgen.IdGen;
import top.zhacker.boot.json.GsonJson;
import top.zhacker.boot.json.Json;
import top.zhacker.boot.registry.DomainRegistry;


/**
 * Created by zhacker.
 * Time 2018/8/26 下午5:46
 */
@Configuration
public class CommonAutoConfig {
  
  @Bean
  @ConditionalOnMissingBean(Json.class)
  public Json json(){
    return new GsonJson();
  }
  
  @Bean
  @ConditionalOnMissingBean(ParamLogAspect.class)
  public ParamLogAspect paramLogAspect(){
    return new ParamLogAspect();
  }
  
  @Bean
  @ConditionalOnMissingBean(DomainRegistry.class)
  public DomainRegistry domainRegistry(){
    return new DomainRegistry();
  }
  
  @Bean
  @ConditionalOnMissingBean(IdGen.class)
  public IdGen idGen(){
    return new CommonSelfIdGenerator();
  }
  
  @Bean
  @ConditionalOnMissingBean(CommonExceptionAspect.class)
  public CommonExceptionAspect commonExceptionAspect(){
    return new CommonExceptionAspect();
  }
}
