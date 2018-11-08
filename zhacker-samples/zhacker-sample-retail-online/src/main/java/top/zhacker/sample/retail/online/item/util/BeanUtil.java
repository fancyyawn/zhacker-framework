package top.zhacker.sample.retail.online.item.util;

import com.alibaba.fastjson.JSON;
import top.zhacker.sample.retail.online.item.common.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;


/**
 * Created by zhacker.
 * Time 2018/2/10 上午10:21
 */
public class BeanUtil {
  
  public static <T> T deepCopy(Object obj, Class<T> clazz){
    String json = JSON.toJSONString(obj);
    return JSON.parseObject(json, clazz);
  }

  public static <T> List<T> deepCopyList(List<?> obj, Class<T> clazz){
    return JSON.parseArray(JSON.toJSONString(obj), clazz);
  }

  public static <T> Page<T> deepCopyPage(Page<?> obj, Class<T> clazz){
    return new Page<>(obj.getTotal(), deepCopyList(obj.getData(), clazz));
  }
  
  
  public static <S,T> T merge(T target, S data){
    Field[] fields = data.getClass().getDeclaredFields();
    for(Field field : fields){
      try {
        field.setAccessible(true);
        Object value = null;
        try {
          value = field.get(data);
        } catch (IllegalAccessException e) {
          //          e.printStackTrace();
        }
        if(value==null){
          continue;
        }
        Field targetField = target.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        try {
          targetField.set(target, value);
        } catch (IllegalAccessException e) {
          //          e.printStackTrace();
        }
      } catch (NoSuchFieldException e) {
        //        e.printStackTrace();
      }
    }
    return target;
  }
  
  public static <S,T> T shadowCopy(S data, Class<T> targetClass){
    return Optional.ofNullable(shadowCopyNullable(data, targetClass)).orElseThrow(()->new IllegalArgumentException("bean.copy.fail"));
  }
  
  

  
  public static <S,T> T shadowCopyNullable(S data, Class<T> targetClass){
    T obj = null;
    try {
      Constructor<T> constructor = targetClass.getDeclaredConstructor();
      constructor.setAccessible(true);
      obj = constructor.newInstance();
    }catch (Exception e){
      return null;
    }
    return merge(obj,data);
  }
}
