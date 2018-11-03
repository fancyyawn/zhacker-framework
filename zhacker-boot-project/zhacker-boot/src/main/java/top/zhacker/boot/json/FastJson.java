package top.zhacker.boot.json;

import com.alibaba.fastjson.JSON;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/8/25 下午2:05
 */
public class FastJson implements Json {
  
  @Override
  public String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }
  
  @Override
  public <T> T fromJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }
  
  @Override
  public <T> List<T> fromJsonList(String jsonList, Class<T> clazz) {
    return JSON.parseArray(jsonList, clazz);
  }
  
}
