package top.zhacker.sample.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:48
 */
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(value = "", defaultConfiguration = LogFeignServiceConfig.class)
@EnableFeignClients({"top.zhacker.sample.consumer.client", "top.zhacker.sample.provider.api"})
public class SampleConsumerApp {
    public static void main(String[] args){
        SpringApplication.run(SampleConsumerApp.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
