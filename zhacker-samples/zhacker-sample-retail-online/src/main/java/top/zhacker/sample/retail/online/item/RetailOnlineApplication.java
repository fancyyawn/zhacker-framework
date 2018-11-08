package top.zhacker.sample.retail.online.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class RetailOnlineApplication {

  public static void main(String[] args) {
    SpringApplication.run(RetailOnlineApplication.class, args);
  }
  
}
