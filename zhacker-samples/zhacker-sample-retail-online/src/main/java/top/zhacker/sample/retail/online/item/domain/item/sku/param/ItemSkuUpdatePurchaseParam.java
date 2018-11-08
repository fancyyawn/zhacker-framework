package top.zhacker.sample.retail.online.item.domain.item.sku.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午4:56
 */
@Data
@Accessors(chain = true)
public class ItemSkuUpdatePurchaseParam {
  
  private Long id;
  
  @ApiModelProperty("成本价")
  private Long costPrice;
  
  @ApiModelProperty("结算价")
  private Long settlePrice;
  
  @ApiModelProperty("新增库存")
  private Long stock;

  @ApiModelProperty("批次号")
  private String batchNo;
  
}
