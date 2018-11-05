package top.zhacker.discovery.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by zhanghecheng on 2018/3/18.
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {

  public static void main(String[] args){
    SpringApplication.run(EurekaApp.class, args);
  }
}
