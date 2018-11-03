package top.zhacker.boot.starter.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


/**
 * Created by zhacker.
 * Time 2018/8/31 下午9:06
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
  String basePackage;
  String pathMapping = "/";
  String title = "Swagger2 API文档";
  String description = "";
  String version = "1.0.0";
}
