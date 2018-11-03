package top.zhacker.core.request;


import java.io.Serializable;


public interface Request extends Serializable {

  /**
   * 请求来源,系统名称或前端终端。
   */
  String getSource();

  /**
   * 操作人
   */
  Operator getOperator();
}