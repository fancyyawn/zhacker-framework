package top.zhacker.core.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by zhacker.
 * Time 2018/1/12 下午6:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
  
  private Long total;
  
  private List<T> data;
}
