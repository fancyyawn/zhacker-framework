package top.zhacker.boot.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;


/**
 * Created by zhacker.
 * Time 2018/8/18 上午10:46
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ListDistinct.ListDistinctChecker.class })
public @interface ListDistinct {
  
  String message() default "列表元素不能重复";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
  
  public static class ListDistinctChecker implements ConstraintValidator<ListDistinct, List<?>> {
  
    @Override
    public void initialize(ListDistinct constraintAnnotation) {
    
    }
  
    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext constraintValidatorContext) {
      return objects.stream().distinct().count()==objects.size();
    }
  
  }
}
