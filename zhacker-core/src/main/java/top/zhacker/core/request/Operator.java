package top.zhacker.core.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/4/19 下午12:36
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Operator implements Serializable {
  
  private Long id;
  private String name;


  public final static String CONTEXT_KEY_USER_ID = "x-user-id";

  public final static String CONTEXT_KEY_USER_NAME = "x-user-name";

  public Operator(Map<String,String> properties){
    this.name = properties.get(CONTEXT_KEY_USER_NAME);
    this.id = Optional.ofNullable(properties.get(CONTEXT_KEY_USER_ID)).map(Long::parseLong).orElse(null);
  }


  /**
   * 将user对象转换成为http对象头
   * @return http头键值对
   */
  public Map<String, String> toHttpHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put(CONTEXT_KEY_USER_ID, String.valueOf(id));
    headers.put(CONTEXT_KEY_USER_NAME, name);
    return headers;
  }
}
