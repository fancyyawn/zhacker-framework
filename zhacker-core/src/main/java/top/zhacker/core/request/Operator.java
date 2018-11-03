package top.zhacker.core.request;

import java.io.Serializable;

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

}
