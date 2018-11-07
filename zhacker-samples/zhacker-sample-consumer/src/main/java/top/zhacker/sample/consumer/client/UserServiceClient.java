package top.zhacker.sample.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import top.zhacker.sample.provider.api.UserService;

/**
 * Created by zhacker.
 * Time 2018/11/7 下午9:00
 */
@FeignClient("sample-provider")
public interface UserServiceClient extends UserService {
}
