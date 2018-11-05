package top.zhacker.eureka.admin;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EurekaAdminConfig.class)
public @interface EnableEurekaAdmin {

}
