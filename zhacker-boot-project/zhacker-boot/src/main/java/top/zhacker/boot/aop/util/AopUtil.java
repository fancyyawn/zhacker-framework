package top.zhacker.boot.aop.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AopUtil {
  public static <T extends Annotation> T getAnnotationPresent(ProceedingJoinPoint pjp, Class<T> annotationClass) {
    Method method = getMethod(pjp);
    T annotation;
    annotation = method != null ? method.getAnnotation(annotationClass) : null;
    if (annotation != null) {
      return annotation;
    } else {
      Class<?> clazz = pjp.getTarget().getClass();
      annotation = clazz != null ? clazz.getAnnotation(annotationClass) : null;
      return annotation;
    }
  }


  public static Method getMethod(ProceedingJoinPoint pjp) {
    Signature sig = pjp.getSignature();
    if (!(sig instanceof MethodSignature)) {
      log.error("getMethod encounter error, unsupport signature:" + sig.getName());
      return null;
    }
    MethodSignature msig = (MethodSignature) sig;
    Object target = pjp.getTarget();

    Method currentMethod = null;
    try {
      currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    } catch (NoSuchMethodException e) {
      log.error("getMethod encounter error, NoSuchMethodException " + sig.getName(), e);
    }
    return currentMethod;
  }

  public static String buildErrorMsg(Set<ConstraintViolation<?>> constraintSet) {

    if (constraintSet != null && !constraintSet.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      constraintSet.forEach(
          c -> sb.append(c.getPropertyPath()).append(":").append(c.getMessage()).append(","));
      return sb.deleteCharAt(sb.length() - 1).toString();
    } else {
      return "";
    }
  }
  
  
  public static String buildErrorDisplayMsg(Set<ConstraintViolation<?>> constraintSet) {
    
    if (constraintSet != null && !constraintSet.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      constraintSet.forEach(c -> sb.append(c.getMessage()).append(","));
      return sb.deleteCharAt(sb.length() - 1).toString();
    } else {
      return "";
    }
  }
}
