package top.zhacker.eureka.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zhanghecheng on 2018/3/18.
 */
@EnableEurekaAdmin
@SpringBootApplication
public class EurekaAdminApp {

  public static void main(String[] args){
    SpringApplication.run(EurekaAdminApp.class, args);
  }
}
