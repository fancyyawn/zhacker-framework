package top.zhacker.sample.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.consumer.client.GithubFeignService;

@RestController
@ParamLog
public class GithubFeignController {

    @Autowired
    private GithubFeignService helloFeignService;

    // 服务消费者对位提供的服务
    @GetMapping(value = "/search/github")
    public ResponseEntity<byte[]> searchGithubRepoByStr(@RequestParam("str") String queryStr) {
        return helloFeignService.searchRepo(queryStr);
    }

}
