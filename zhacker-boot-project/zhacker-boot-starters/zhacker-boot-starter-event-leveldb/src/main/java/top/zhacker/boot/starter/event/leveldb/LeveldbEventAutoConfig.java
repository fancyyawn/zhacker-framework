package top.zhacker.boot.starter.event.leveldb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.zhacker.boot.leveldb.LevelDbTransactionalAspect;


/**
 * Created by zhacker.
 * Time 2018/7/1 下午4:57
 */
@Configuration
@ComponentScan
public class LeveldbEventAutoConfig {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(name = "event.store", havingValue = "leveldb")
  public LevelDbTransactionalAspect levelDbTransactionalAspect(){
    return new LevelDbTransactionalAspect();
  }
  
}
