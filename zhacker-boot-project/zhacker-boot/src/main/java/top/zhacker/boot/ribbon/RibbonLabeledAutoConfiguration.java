package top.zhacker.boot.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 
 * DefaultRibbonConfiguration 加载LWR规则
 */
@Configuration
@EnableWebMvc
@RibbonClients(defaultConfiguration = RibbonLabeledAutoConfiguration.RibbonWithNewRuleConfiguration.class)
public class RibbonLabeledAutoConfiguration extends WebMvcConfigurerAdapter {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new LabelHeaderHttpRequestInterceptor());
		return restTemplate;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LabelHeaderMvcHandlerInterceptor());
	}
	
	
	/**
	 *
	 *
	 */
	public static class RibbonWithNewRuleConfiguration {
		
		@Value("${ribbon.client.name:#{null}}")
		private String name;
	
		@Autowired(required = false)
		private IClientConfig config;
	
		@Autowired
		private PropertiesFactory propertiesFactory;
	
		@Bean
		public IRule ribbonRule() {
			if (StringUtils.isEmpty(name)) {
				return null;
			}
	
			if (this.propertiesFactory.isSet(IRule.class, name)) {
				return this.propertiesFactory.get(IRule.class, config, name);
			}
	
			// LWR 默认配置
			LabelAndWeightMetadataRule rule = new LabelAndWeightMetadataRule();
			rule.initWithNiwsConfig(config);
			return rule;
		}
	}
}
