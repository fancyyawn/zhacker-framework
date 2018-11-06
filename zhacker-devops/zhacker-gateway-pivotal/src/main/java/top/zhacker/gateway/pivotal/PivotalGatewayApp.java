package top.zhacker.gateway.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by zhacker.
 * Time 2018/11/6 下午12:28
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PivotalGatewayApp {

    public static void main(String[] args){
        SpringApplication.run(PivotalGatewayApp.class, args);
    }
}
