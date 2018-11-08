package top.zhacker.sample.retail.online.item.domain.item.param;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/12 下午9:09
 */
@Data
@Accessors(chain = true)
public class ItemUpdateStatusParam {
  
  private Long itemId;
  
  private Integer status;
}
