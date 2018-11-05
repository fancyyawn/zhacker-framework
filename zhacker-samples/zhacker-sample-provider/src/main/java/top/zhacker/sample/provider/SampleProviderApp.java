package top.zhacker.sample.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class SampleProviderApp {

    public static void main(String[] args){
        SpringApplication.run(SampleProviderApp.class, args);
    }
}
