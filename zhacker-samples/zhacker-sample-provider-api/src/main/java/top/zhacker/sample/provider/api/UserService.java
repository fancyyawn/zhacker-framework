package top.zhacker.sample.provider.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:40
 */
@FeignClient(name = "sample-provider")
public interface UserService {

    @GetMapping("/user/page")
    List<User> page(UserPageRequest request);

    @PostMapping("/user")
    Long create(@RequestBody User user);

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

}
