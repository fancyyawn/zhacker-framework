package top.zhacker.sample.retail.online.item.common;

import java.util.EnumSet;


/**
 * Created by zhacker.
 * Time 2018/1/5 下午5:47
 */
public interface Checker<T> {
  
  void check(T t);
  
  default EnumSet<Operation> support(){
    return Operation.saveSet();
  }
}
