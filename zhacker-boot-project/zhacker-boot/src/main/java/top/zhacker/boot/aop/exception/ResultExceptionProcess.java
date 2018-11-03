package top.zhacker.boot.aop.exception;


import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import top.zhacker.boot.aop.util.AopUtil;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.core.response.Result;


public abstract class ResultExceptionProcess implements ExceptionProcess {



  protected String getMessage(Throwable e){
      if (e instanceof BusinessException){
        return ((BusinessException)e).getDesc();
      }else if (e instanceof ConstraintViolationException){
        Set<ConstraintViolation<?>> errors =
            ((ConstraintViolationException) e).getConstraintViolations();
        return "参数错误 : " + AopUtil.buildErrorDisplayMsg(errors);
      }else{
        return "服务器错误";
      }

  }

  protected Integer getCode(Throwable e){
    if (e instanceof BusinessException){
      return ((BusinessException)e).getCode();
    }
    return 1;
  }


  protected Map<String, Object> getErrData(Throwable e) {
    if (e instanceof BusinessException) {
      return ((BusinessException) e).getErrorData();
    }
    return null;
  }

  @Override
  public Object processException(Throwable e) {
    return Result.fail(getCode(e).toString(), getMessage(e));
  }

}
