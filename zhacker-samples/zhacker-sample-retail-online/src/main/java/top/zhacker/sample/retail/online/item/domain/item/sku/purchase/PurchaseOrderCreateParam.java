package top.zhacker.sample.retail.online.item.domain.item.sku.purchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/10 上午9:06
 */
@Data
@Accessors(chain = true)
public class PurchaseOrderCreateParam {
  
  @ApiModelProperty("供应商ID")
  private Long vendorId;
  
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("规格ID")
  private Long skuId;
  
  @ApiModelProperty("成本价")
  private Long costPrice;
  
  @ApiModelProperty("结算价")
  private Long settlePrice;
  
  @ApiModelProperty("进货数量")
  private Long quantity;
  
}
