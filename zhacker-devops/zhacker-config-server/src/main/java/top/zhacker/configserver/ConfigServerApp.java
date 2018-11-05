package top.zhacker.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by zhacker.
 * Time 2018/11/4 下午10:46
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {

    public static void main(String[] args){
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
