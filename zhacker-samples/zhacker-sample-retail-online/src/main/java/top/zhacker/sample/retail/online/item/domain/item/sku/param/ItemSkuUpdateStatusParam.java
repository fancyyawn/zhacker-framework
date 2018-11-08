package top.zhacker.sample.retail.online.item.domain.item.sku.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/11 上午8:37
 */
@Data
@Accessors(chain = true)
public class ItemSkuUpdateStatusParam {
  
  @ApiModelProperty("规格ID")
  private Long id;
  
  @ApiModelProperty("规格状态")
  private Integer status;
  
}
