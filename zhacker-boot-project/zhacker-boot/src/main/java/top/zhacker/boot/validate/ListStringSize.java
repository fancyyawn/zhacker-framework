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
@Constraint(validatedBy = { ListStringSize.ListStringSizeChecker.class })
public @interface ListStringSize {
  
  int min() default 0;
  
  int max() default Integer.MAX_VALUE;
  
  String message() default "列表元素大小不符合定义";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
  
  public static class ListStringSizeChecker implements ConstraintValidator<ListStringSize, List<String>> {
  
    ListStringSize annotation;
    
    @Override
    public void initialize(ListStringSize constraintAnnotation) {
      annotation = constraintAnnotation;
    }
  
    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext constraintValidatorContext) {
      if(objects==null){
        return true;
      }
      return objects.stream().allMatch(s-> s.length()<=annotation.max() && s.length()>=annotation.min());
    }
  
  }
}
