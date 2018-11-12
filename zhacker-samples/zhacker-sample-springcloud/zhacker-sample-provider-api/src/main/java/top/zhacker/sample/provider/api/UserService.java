package top.zhacker.sample.provider.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.zhacker.core.response.Result;

import java.util.List;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:40
 */
public interface UserService {

    @GetMapping("/user/page")
    Result<List<UserVO>> page(UserPageRequest request);

    @PostMapping("/user")
    Result<Long> create(@RequestBody UserCreateRequest user);

    @GetMapping("/user/{id}")
    Result<UserVO> findById(@PathVariable("id") Long id);

}
