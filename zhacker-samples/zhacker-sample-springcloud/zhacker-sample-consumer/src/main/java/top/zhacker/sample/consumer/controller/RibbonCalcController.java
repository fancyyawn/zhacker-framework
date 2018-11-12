package top.zhacker.sample.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.zhacker.boot.aop.log.ParamLog;

@RestController
@ParamLog
public class RibbonCalcController {
	
    @Autowired
    private RestTemplate restTemplate;

	@GetMapping("/add")
	public String add(Integer a, Integer b) {
		String result = restTemplate
				.getForObject("http://sample-provider/add?a=" + a + "&b=" + b, String.class);
		System.out.println(result);
		return result;
	}
}