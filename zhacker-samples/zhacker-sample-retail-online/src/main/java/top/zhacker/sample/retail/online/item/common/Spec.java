package top.zhacker.sample.retail.online.item.common;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.registry.DomainRegistry;


/**
 * Created by zhacker.
 * Time 2018/2/3 下午3:46
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { Spec.SpecChecker.class })
public @interface Spec {
  
  Class<? extends Checker>[] value();
  
  String message() default "字段不符合条件约束";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
  
  @Slf4j
  public static class SpecChecker implements ConstraintValidator<Spec, Object> {
    
    Spec annotation;
    
    List<Class<? extends Checker>> checkerClasses;
  
    Map<Class<? extends Checker>, ? extends Checker> allCheckers;
    
    @Override
    public void initialize(Spec constraintAnnotation) {
      annotation = constraintAnnotation;
      checkerClasses = Arrays.asList(annotation.value());
      allCheckers = DomainRegistry.beanMap(Checker.class)
          .values().stream()
          .collect(Collectors.toMap(Checker::getClass, Function.identity()));
    }
    
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
      try {
        StringBuilder sb = new StringBuilder();
        checkerClasses.forEach(c->{
          Checker checker = allCheckers.get(c);
          if(checker!=null){
            try {
              checker.check(object);
            }catch (Exception e){
              sb.append(e.getMessage()).append(" ");
            }
          }
        });
        if(sb.length()>0) {
          constraintValidatorContext.disableDefaultConstraintViolation();
          constraintValidatorContext
              .buildConstraintViolationWithTemplate(sb.toString())
              .addConstraintViolation();
          return false;
        }else{
          return true;
        }
      }catch (Exception e){
        log.warn("", e);
        return false;
      }
    }
    
  }
}
