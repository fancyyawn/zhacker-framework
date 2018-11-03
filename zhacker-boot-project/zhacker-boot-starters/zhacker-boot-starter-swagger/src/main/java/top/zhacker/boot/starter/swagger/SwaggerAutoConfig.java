package top.zhacker.boot.starter.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by zhacker.
 * Time 2018/1/14 下午2:34
 */
@EnableSwagger2
@Configuration
@Import({BeanValidatorPluginsConfiguration.class})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfig {
  
  @Bean
  public Docket createRestApiLocal(SwaggerProperties properties) {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo(properties))
        .select()
        .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))//api接口包扫描路径
        .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
        .build()
        .pathMapping(properties.getPathMapping());
  }
  
  private ApiInfo apiInfo(SwaggerProperties properties) {
    return new ApiInfoBuilder()
        .title(properties.getTitle())//设置文档的标题
        .description(properties.getDescription())//设置文档的描述->1.Overview
        .version(properties.getVersion())//设置文档的版本信息-> 1.1 Version information
//        .contact(new Contact("ABC Boot", "http://www.abc.comt", ""))//设置文档的联系方式->1.2 Contact information
//        .termsOfServiceUrl("www.abc.com")//设置文档的License信息->1.3 License information
        .build();
  }
}
