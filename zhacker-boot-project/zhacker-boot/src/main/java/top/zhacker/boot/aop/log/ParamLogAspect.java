package top.zhacker.boot.aop.log;

import com.google.common.base.Strings;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.zhacker.boot.aop.util.AopUtil;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.core.exception.ErrorLevel;
import top.zhacker.core.request.BaseRequest;
import top.zhacker.core.request.Operator;
import top.zhacker.core.request.Request;

@Aspect
@Order(2)
@Slf4j
public class ParamLogAspect {

  private static final Logger PARAM_API = LoggerFactory.getLogger("PARAM");
  private static final int SLOW_QUERY = 100;
  
  private Set<Integer> globalWarnCode = new HashSet<>();

  private ConcurrentMap<String, CopyOnWriteArraySet<Integer>> methodWarnCode = new ConcurrentHashMap<>();

  private Map<String, Collection<Integer>> partErrorCode = new HashMap<>();


  @Setter
  private boolean errorPrintRpcContext = false;

  private static final String USER_AGENT = "User-Agent";


  /**
   * method 和 class 都可以被切面到，如果一个类已经被 类处理过了 那么切面不在处理。
   */
  private static final ThreadLocal<Map<String, Local>> processMethod = ThreadLocal.withInitial(HashMap::new);

  @Around("@annotation(top.zhacker.boot.aop.log.ParamLog)")
  public Object doRequestMethod(ProceedingJoinPoint pjp) throws Throwable {
    return execWithLog(pjp);
  }

  @Around("@within(top.zhacker.boot.aop.log.ParamLog)")
  public Object doRequestClass(ProceedingJoinPoint pjp) throws Throwable {
    return execWithLog(pjp);
  }


  private Object execWithLog(ProceedingJoinPoint pjp) throws Throwable {
    String name = pjp.getSignature().getName();
    Map<String, Local> methodMap = processMethod.get();
    Local local = methodMap.get(name);
    boolean outermost = false;
    //如果 没有执行过切面那么执行
    if (local == null) {
      outermost = true;
    }

    try {
      methodMap.put(name, new Local());
      //处理App 切入参数
      convertAndFillArgs(pjp);

      long startTime = System.currentTimeMillis();
      Object[] args = pjp.getArgs();
      String methodName = pjp.getSignature().toShortString();

      ParamLog annotation = AopUtil.getAnnotationPresent(pjp, ParamLog.class);
      if (annotation.open()) {
        try {

          Object response = pjp.proceed();
          logParam(annotation, startTime, methodName, args, response, null, outermost);
          return response;
        } catch (Exception e) {
          logParam(annotation, startTime, methodName, args, null, e, outermost);
          throw e;
        } catch (Throwable e) {
          PARAM_API.error("paramLog has Error ", e);
          throw e;
        }
      } else {
        PARAM_API.info("has print log method:{}", name);
        return pjp.proceed();
      }
    } finally {
      //保底 remove 掉threadLocal
      if (outermost) {
        processMethod.remove();
      }
    }


  }

  /**
   * 转化填充参数
   */
  private void convertAndFillArgs(ProceedingJoinPoint pjp) {

    String userAgent = "";
    if (null != pjp.getArgs()) {
      for (Object arg : pjp.getArgs()) {
        if (arg != null && arg instanceof BaseRequest) {
          BaseRequest request = (BaseRequest) arg;

          if (Strings.isNullOrEmpty(((Request) arg).getSource())) {
            if (Strings.isNullOrEmpty(userAgent) && Strings.isNullOrEmpty(((Request) arg).getSource())) {
              return;
            }
            request.setSource(Strings.isNullOrEmpty(request.getSource())? userAgent : request.getSource());
          }
        }
        break;
      }
    }
  }


  /**
   * 记录方法名称 入参,返回参数
   *
   * @param startTime  开始时间
   * @param methodName 方法名称
   * @param args       参数
   * @param response   返回值
   * @param e          异常可以为null
   */
  private void logParam(ParamLog annotation, long startTime, String methodName, Object[] args, Object response,
      Exception e, boolean outermost) {

    long execTime = (System.currentTimeMillis() - startTime);

    //获取初始化参数
    Map<String, Object> param = initParam(methodName, args, e, execTime);

    //获取子参数
    Map<String, Object> subParams = processParam(methodName, args);
    if (subParams != null) {
      param.putAll(subParams);
    }

    //添加本地日志
    addLocalLog(annotation, methodName, args, response, param);

    //获取返回值
    String str = getResponseContent(annotation, param);

    //打印本地日志
    printLog(methodName, e, str, outermost, annotation.onlyOutermostPrint());

  }

  private void addLocalLog(ParamLog annotation, String methodName, Object[] args, Object response,
      Map<String, Object> param) {
    param.put("method", methodName);
    param.put("param", args);
    if (annotation.logResponse()) {
      param.put("response", response);
    }
  }

  private Map<String, Object> initParam(String methodName, Object[] args, Exception e, long execTime) {
    Map<String, Object> param =new HashMap<>();

    param.put("execTime", execTime);

    if (isSlowRequest(execTime)) {
      param.put("slowQuery", true);
    } else {
      param.put("slowQuery", false);
    }
    String result;

    if (e == null) {
      result = "ok";
    } else {
      if (isWarnException(methodName, e)) {
        result = "failed";
      } else {
        result = "error";
      }
      param.put("code", getCode(e));
      param.put("exception", getMessage(e));
    }
    param.put("result", result);
    if (args != null) {
      for (Object arg : args) {
        if (arg != null && arg instanceof Request) {
          Request request = (Request) arg;
          param.put("adminId", Optional.ofNullable(request.getOperator()).map(Operator::getId).orElse(null));
          param.put("source", request.getSource());
          break;
        }
      }

    }
    return param;
  }

  private void printLog(String methodName, Exception e, String str, boolean outermost, boolean onlyOutermostPrint) {
    if (e == null) {
      if (!onlyOutermostPrint || outermost) {
        PARAM_API.info(str);
      }
    } else {
      String exceptionInfo = getExceptionInfo(e);
      if (e instanceof ConstraintViolationException) {
        if (!onlyOutermostPrint || outermost) {
          PARAM_API.warn(str + ", exception=" + exceptionInfo);
        }

      } else if (isWarnException(methodName, e)) {
        if (!onlyOutermostPrint || outermost) {
          PARAM_API.warn(str + ", exception=" + exceptionInfo);
        }
      } else {
        if (e instanceof BusinessException) {
          logBizEx((BusinessException) e, exceptionInfo, str, !onlyOutermostPrint || outermost);
        } else {
          PARAM_API.error(str + ", exception=" + exceptionInfo, e);
        }
      }
    }
  }

  private void logBizEx(BusinessException ex, String exceptionInfo, String str, boolean outermost) {
    ErrorLevel errorLevel = ex.getErrorLevel();
    if (null != errorLevel && outermost) {
      switch (errorLevel) {
        case INFO:
          PARAM_API.info(str + ", exception=" + exceptionInfo, ex);
          break;
        case WARN:
          PARAM_API.warn(str + ", exception=" + exceptionInfo, ex);
          break;
        case DEBUG:
          PARAM_API.debug(str + ", exception=" + exceptionInfo, ex);
          break;
        default:
          PARAM_API.error(str + ", exception=" + exceptionInfo, ex);
          break;
      }
    } else {
      PARAM_API.error(str + ", exception=" + exceptionInfo, ex);
    }
  }

  private String getResponseContent(ParamLog annotation, Map<String, Object> param) {
    String str = JSON.toJSONString(param);
    return str;
  }

  protected Map<String, Object> processParam(String methodName, Object[] args) {
    return null;
  }

  private String getExceptionInfo(Exception e) {
    if (e != null) {

      Integer code = getCode(e);

      String message = getMessage(e);

      return " class: " + e.getClass() + " , code=" + code + " , message=" + message;
    } else {
      return "";
    }

  }

  private String getMessage(Exception e) {
    String message = e.getMessage();
    if (e instanceof BusinessException) {
      message = ((BusinessException) e).getDesc();
    } else if (e instanceof ConstraintViolationException) {
      Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) e).getConstraintViolations();
      message = "参数错误 : " + AopUtil.buildErrorMsg(errors);
    }
    return message;
  }

  private Integer getCode(Exception e) {
    Integer code = 0;
    if (e instanceof BusinessException) {
      code = ((BusinessException) e).getCode();
    } else if (e instanceof ConstraintViolationException) {
      code = -101;
    }
    return code;
  }


  protected boolean isSlowRequest(long execTime) {
    return execTime > SLOW_QUERY;
  }


  protected boolean isWarnException(String methodName, Exception e) {
    if (e instanceof BusinessException) {
      Integer code = ((BusinessException) e).getCode();
      if (code != null) {
        return isWarnCode(methodName, code);
      }
    } else if (e instanceof ConstraintViolationException) {
      return true;
    }
    return false;
  }

  private boolean isWarnCode(String methodName, Integer code) {
    boolean isWarnCode = globalWarnCode.contains(code);
    if (!isWarnCode) {
      CopyOnWriteArraySet<Integer> errorCodes = methodWarnCode.get(methodName);
      if (errorCodes == null) {
        errorCodes = initMethodWarnCode(methodName);
        methodWarnCode.putIfAbsent(methodName, errorCodes);
      }
      isWarnCode = errorCodes.contains(code);
    }

    return isWarnCode;
  }

  private CopyOnWriteArraySet<Integer> initMethodWarnCode(String methodName) {
    CopyOnWriteArraySet<Integer> errorCodes = new CopyOnWriteArraySet<>();
    partErrorCode.forEach((key, value) -> {
      if (methodName.contains(key.toLowerCase())) {
        errorCodes.addAll(value);
      }
    });
    return errorCodes;
  }

  public synchronized void setPartErrorCode(Map<String, Collection<Integer>> partErrorCode) {
    this.partErrorCode = partErrorCode;
    int oldSize = methodWarnCode.size();
    //刷新缓存
    methodWarnCode = new ConcurrentHashMap<>(oldSize);

  }

  public void setGlobalWarnCode(Collection<Integer> globalWarnCode) {
    this.globalWarnCode = new HashSet<>(globalWarnCode);
  }


  static class Local {

    volatile boolean process;
  }

}
