package top.zhacker.sample.consumer.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.consumer.client.HystrixUserService;
import top.zhacker.sample.provider.api.UserVO;
import top.zhacker.sample.provider.api.UserCreateRequest;
import top.zhacker.sample.provider.api.UserPageRequest;
import top.zhacker.sample.provider.api.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
@ParamLog
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HystrixUserService hystrixUserService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Long addUser( @RequestBody @ApiParam(name="用户",value="传入json格式",required=true) UserCreateRequest user){
		return userService.create(user).getResult();
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public List<UserVO> pageUser(@RequestBody @ApiParam(name="用户",value="传入json格式",required=true) UserPageRequest request){
		return userService.page(request).getResult();
	}

	@GetMapping("/{id}")
	public UserVO findById(@PathVariable("id") Long id){
		return userService.findById(id).getResult();
	}


	@GetMapping("/getUser")
	public String getUser(@RequestParam String username) throws Exception{
		return hystrixUserService.getUser(username);
	}



}
