package top.zhacker.boot.json;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/8/25 下午2:03
 */
public interface Json {
  String toJson(Object obj);
  <T> T fromJson(String json, Class<T> clazz);
  <T> List<T> fromJsonList(String jsonList, Class<T> clazz);
}
