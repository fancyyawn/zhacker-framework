package top.zhacker.sample.retail.online.item.domain.item.sku.param;

import top.zhacker.sample.retail.online.item.domain.item.sku.SellPriceRule;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/11 上午8:33
 */
@Data
@Accessors(chain = true)
public class ItemSkuUpdateSellParam {
  
  @ApiModelProperty("规格ID")
  private Long id;
  
  @ApiModelProperty("销售价划分规则")
  private SellPriceRule sellPriceRule;
  
  @ApiModelProperty("销售价")
  private Long sellPrice;

  @ApiModelProperty("原价")
  private Long originPrice;
  
  @ApiModelProperty("规格营销属性")
  @NotNull
  private Long diamond;
  
}
