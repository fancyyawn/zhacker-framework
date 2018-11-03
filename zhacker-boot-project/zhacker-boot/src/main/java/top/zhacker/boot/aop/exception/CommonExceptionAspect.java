package top.zhacker.boot.aop.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.aop.util.AopUtil;


@Aspect
@Order(1)
@Slf4j
public class CommonExceptionAspect {


  private List<ExceptionProcess>
      DEFAULT_EXCEPTION_PROCESS = new ArrayList<>();

  private List<ExceptionProcess> userExceptionProcess;

  /**
   * 初始化后不应该在修改，所以不存在多线程问题
   */
  private Map<Class<?>,ExceptionProcess> map = new HashMap<>();

  @PostConstruct
  public void init(){
    for (ExceptionProcess process : DEFAULT_EXCEPTION_PROCESS) {
      map.put(process.processClass(),process);
    }

    if (userExceptionProcess!=null){
      for (ExceptionProcess process : userExceptionProcess) {
        map.put(process.processClass(),process);
      }
    }
  }

  private boolean outputError=false;


  @Around("@annotation(top.zhacker.boot.aop.exception.ProcessException)")
  public Object doRequestMethod(ProceedingJoinPoint pjp) throws Throwable {
    return processException(pjp);
  }

  @Around("@within(top.zhacker.boot.aop.exception.ProcessException)")
  public Object doRequestClass(ProceedingJoinPoint pjp) throws Throwable {
    return processException(pjp);
  }

  /**
   * 查看类是否为指定类
   */
  private Object processException(ProceedingJoinPoint pjp) throws Throwable {
    Method method = AopUtil.getMethod(pjp);
    Class<?> returnType=null;
    if (method!=null){
      returnType = method.getReturnType();
    }

    ProcessException annotation = AopUtil.getAnnotationPresent(pjp, ProcessException.class);
    if (annotation != null) {
      try {
        return pjp.proceed();
      } catch (Throwable e) {
        Object result;
        if (returnType!=null){
          ExceptionProcess exceptionProcess = map.get(returnType);
          if (exceptionProcess==null){
            throw  e;
          }
          result = exceptionProcess.processException(e);
          if (outputError || annotation.printError()) {
            log.error("处理异常 result="+result.toString(),e);
          }
          return result;

        }
        throw  e;
      }
    } else {
      return pjp.proceed();
    }

  }


  public void setUserExceptionProcess(
      List<ExceptionProcess> userExceptionProcess) {
    this.userExceptionProcess = userExceptionProcess;
  }

  public void setOutputError(boolean outputError) {
    this.outputError = outputError;
  }
}
