package top.zhacker.sample.retail.online.item.domain.item.exhibit;

import top.zhacker.sample.retail.online.item.domain.item.sku.SellPriceRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午12:43
 */
@Data
@Accessors(chain = true)
public class ExhibitionItemSku {
  
  @ApiModelProperty("会场ID")
  private Long exhibitionId;
  
  @ApiModelProperty("规格ID")
  private Long skuId;
  
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("销售价")
  private Long sellPrice;
  
  @ApiModelProperty("销售价划分规则")
  private SellPriceRule sellPriceRule;
  
  @ApiModelProperty("返回的点金石")
  private Long diamond;
  
}
