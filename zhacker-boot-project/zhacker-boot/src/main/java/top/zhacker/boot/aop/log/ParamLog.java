package top.zhacker.boot.aop.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ParamLog {

  boolean open() default true;

  boolean logResponse() default true;

  boolean logDigestInfo() default false;

  boolean onlyOutermostPrint() default true;
}
