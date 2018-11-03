package top.zhacker.core.response;

import java.io.Serializable;

import top.zhacker.core.exception.BusinessException;


public class Result<T> implements Serializable {

  private T result;
  private String error;
  private String errorCode;

  public Result() {
  }

  public boolean isSuccess() {
    return result != null;
  }

  public boolean isEmpty(){
    return result == null && error==null;
  }

  public T orException(){
    if(! isSuccess()){
      throw new BusinessException(error);
    }else{
      return this.result;
    }
  }

  public T getResult() {
    return this.result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }
  
  public void setErrorCode(String errorCode){
    this.errorCode = errorCode;
  }
  
  public String getErrorCode(){
    return this.errorCode;
  }

  public String toString() {
    return String.format("success=%s, result=%s, errorCode=%s, error=%s", this.isSuccess(), this.result, this.errorCode, this.error);
  }

  
  public static <T> Result<T> ok(T data) {
    Result<T> resp = new Result<>();
    resp.setResult(data);
    return resp;
  }

  public static <T> Result<T> ok() {
    return ok(null);
  }

  public static <T> Result<T> fail(String error) {
    Result<T> resp = new Result<>();
    resp.setError(error);
    return resp;
  }
  
  public static <T> Result<T> fail(String errorCode ,String error) {
    Result<T> resp = new Result<>();
    resp.setError(error);
    resp.setErrorCode(errorCode);
    return resp;
  }
  
}