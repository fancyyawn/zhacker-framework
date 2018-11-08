package top.zhacker.sample.retail.online.item.common;

import java.util.EnumSet;


/**
 * Created by zhacker.
 * Time 2018/1/5 上午11:09
 */
public enum Operation {
  Unknown,Create,Update,Delete,Query;
  
  public boolean isNew(){
    return this.equals(Create);
  }
  
  public static EnumSet<Operation> saveSet(){
    return EnumSet.of(Operation.Create, Operation.Update);
  }
}
