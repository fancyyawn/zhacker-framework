package top.zhacker.sample.retail.online.item.common;

import top.zhacker.core.model.AssertionConcern;
import top.zhacker.sample.retail.online.item.util.BeanUtil;

import static top.zhacker.sample.retail.online.item.util.BeanUtil.shadowCopy;


/**
 * Created by zhacker.
 * Time 2018/2/13 下午3:26
 */
public class BaseEntity<T> extends AssertionConcern {
  
  public static <S,T> T from(S data, Class<T> targetClass){
    return shadowCopy(data, targetClass);
  }
  
  public <S> T merge(S obj){
    return BeanUtil.merge((T)this, obj);
  }
}
