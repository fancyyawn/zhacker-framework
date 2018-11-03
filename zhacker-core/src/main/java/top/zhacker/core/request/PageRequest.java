package top.zhacker.core.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/12 下午8:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class PageRequest extends BaseRequest {
  
  private Integer pageNo = 1;
  
  private Integer pageSize = 20;
  
  public Integer getLimit(){
    return pageSize;
  }
  
  public Integer getOffset(){
    return (pageNo-1)*pageSize;
  }
}
