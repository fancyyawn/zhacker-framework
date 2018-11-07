package top.zhacker.sample.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.zhacker.core.response.Result;
import top.zhacker.sample.provider.api.UserCreateRequest;
import top.zhacker.sample.provider.api.UserPageRequest;
import top.zhacker.sample.provider.api.UserService;
import top.zhacker.sample.provider.api.UserVO;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:45
 */
@Slf4j
@RestController
public class UserServiceImpl implements UserService {

    @Override
    public Result<List<UserVO>> page(UserPageRequest request) {
        log.info("{}", request);
        return Result.ok(Arrays.asList(new UserVO().setId(1L).setName("zhang").setAge(request.getAge())));
    }

    @Override
    public Result<Long> create(@RequestBody UserCreateRequest user) {
        log.info("{}", user);
        return Result.ok(user.getId());
    }

    @Override
    public Result<UserVO> findById(@PathVariable("id") Long id) {
        return Result.ok(new UserVO().setId(id).setName("zhang").setAge(10));
    }
}
