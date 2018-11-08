package top.zhacker.sample.retail.online.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


/**
 * Created by zhacker.
 * Time 2018/1/13 下午12:22
 */
@ComponentScan
@Configuration
@MapperScan("top.zhacker.sample.retail.online.item")
public class ItemConfig {
  /**
   * 参数校验
   */
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }
  
}
