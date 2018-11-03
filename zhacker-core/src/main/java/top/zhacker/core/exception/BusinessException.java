package top.zhacker.core.exception;

import java.util.Map;


/**
 * 运行时业务异常。
 */
public class BusinessException extends RuntimeException {

  /**
   * 错误编码
   */
  protected Integer code;

  /**
   * 描述用于展示
   */
  protected String desc;

  /**
   * 错误消息
   */
  protected String message;


  /**
   * 调用失败返回的数据
   */
  private Map<String, Object> errorData;

  /**
   * 国际化语言
   */
  protected String lang = "zh-cn";

  /**
   * 日志错误等级
   */
  protected  ErrorLevel errorLevel;


  public BusinessException(Integer code, String message, String desc, Throwable throwable) {
    super(message, throwable);
    this.code = code;
    this.message = message;
    this.desc = desc;
  }

  public BusinessException(Integer code, String message, String desc, Map<String, Object> errorData) {
    super(message);
    this.code = code;
    this.message = message;
    this.desc = desc;
    this.errorData = errorData;
  }

  public BusinessException(Integer code, String message, String desc, Map<String, Object> errorData,ErrorLevel errorLevel) {
    super(message);
    this.code = code;
    this.message = message;
    this.desc = desc;
    this.errorData = errorData;
    this.errorLevel=errorLevel;
  }

  public BusinessException(Integer code, String message, String desc) {

    super(message);
    this.code = code;
    this.message = message;
    this.desc = desc;
  }

  public BusinessException(Integer code, String message, String desc,ErrorLevel errorLevel) {

    super(message);
    this.code = code;
    this.message = message;
    this.desc = desc;
    this.errorLevel=errorLevel;
  }

  public BusinessException() {
    this(99997, "内部错误", "内部错误");
  }

  public BusinessException(Throwable throwable) {
    this(99997, "内部错误", "内部错误", throwable);
  }

  public BusinessException(String message) {
    this(99997, message, "内部错误");
  }


  public BusinessException(Integer code, String message, Throwable throwable) {
    this(code, message, message, throwable);
  }

  public BusinessException(Integer code, String message) {
    this(code, message, message);
  }

  public BusinessException(ErrorCode errorCode) {
    this(errorCode.getCode(), errorCode.getMessage(), errorCode.getDesc());
  }

  public BusinessException(ErrorCode errorCode, Throwable throwable) {
    this(errorCode.getCode(), errorCode.getMessage(), errorCode.getDesc(), throwable);
  }

  public BusinessException(ErrorCode errorCode, String message) {
    this(errorCode.getCode(), message, errorCode.getDesc());
  }

  public BusinessException(ErrorCode errorCode, String message,ErrorLevel errorLevel) {
    this(errorCode.getCode(), message, errorCode.getDesc(),errorLevel);
  }

  public BusinessException(ErrorCode errorCode, String message, Throwable throwable) {
    this(errorCode.getCode(), message, errorCode.getDesc(), throwable);
  }

  public BusinessException(ErrorCode errorCode, String message, Map<String, Object> errorData) {
    this(errorCode.getCode(), message, errorCode.getDesc(), errorData);
  }

  public BusinessException(ErrorCode errorCode, String message, Map<String, Object> errorData,ErrorLevel errorLevel) {
    this(errorCode.getCode(), message, errorCode.getDesc(), errorData,errorLevel);
  }


  public Integer getCode() {
    return code;
  }


  public String getDesc() {
    return desc;
  }

  public String getLang() {
    return lang;
  }

  public Map<String, Object> getErrorData() {
    return errorData;
  }

  public ErrorLevel getErrorLevel(){
    return errorLevel;
  }
}
