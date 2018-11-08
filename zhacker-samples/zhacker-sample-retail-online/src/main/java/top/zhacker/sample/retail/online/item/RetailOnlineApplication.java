package top.zhacker.sample.retail.online.item;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import top.zhacker.sample.retail.online.item.config.CustomUrlBlockHandler;


@EnableDiscoveryClient
@SpringBootApplication
public class RetailOnlineApplication {

  public static void main(String[] args) {
    SpringApplication.run(RetailOnlineApplication.class, args);
    WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
  }
  
}
