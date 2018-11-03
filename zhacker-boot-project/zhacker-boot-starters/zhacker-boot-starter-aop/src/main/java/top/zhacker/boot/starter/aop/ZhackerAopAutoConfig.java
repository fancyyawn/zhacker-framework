package top.zhacker.boot.starter.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import top.zhacker.boot.starter.CommonAutoConfig;


/**
 * Created by zhacker.
 * Time 2018/8/31 下午6:14
 */
@Configuration
@Import(CommonAutoConfig.class)
public class ZhackerAopAutoConfig {
  
}
