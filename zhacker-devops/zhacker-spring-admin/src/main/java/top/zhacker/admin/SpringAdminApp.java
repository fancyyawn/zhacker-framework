package top.zhacker.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by zhacker.
 * Time 2018/11/5 下午5:08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class SpringAdminApp {

    public static void main(String[] args){
        SpringApplication.run(SpringAdminApp.class, args);
    }
}
