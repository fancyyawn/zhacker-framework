package top.zhacker.boot.aop.exception;


public interface ExceptionProcess {

  Class<?> processClass();

  Object processException(Throwable e);
}
