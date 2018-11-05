package top.zhacker.sample.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.zhacker.sample.provider.api.User;
import top.zhacker.sample.provider.api.UserPageRequest;
import top.zhacker.sample.provider.api.UserService;

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
    public List<User> page(UserPageRequest request) {
        log.info("{}", request);
        return Arrays.asList(new User().setId(1L).setName("zhang").setAge(request.getAge()));
    }

    @Override
    public Long create(User user) {
        log.info("{}", user);
        return user.getId();
    }

    @Override
    public User findById(Long id) {
        return new User().setId(id).setName("zhang").setAge(10);
    }
}
