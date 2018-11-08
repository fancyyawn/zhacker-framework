package top.zhacker.sample.retail.online.item.rest.item.vo;

import top.zhacker.sample.retail.online.item.domain.price.PriceUtil;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ItemSkuVO implements Serializable{

  @ApiModelProperty("规格ID")
  private Long id;

  @ApiModelProperty("商品ID")
  private Long itemId;

  @ApiModelProperty("规格定义列表")
  private List<SkuSpecKeyValue> specs;

  @ApiModelProperty("销售价")
  private Long sellPrice;

  public String getSellPriceYuan(){
    return PriceUtil.formatYuan(sellPrice);
  }

  @ApiModelProperty("销售价划分规则")
  private SellPriceRuleVO sellPriceRule;

  @ApiModelProperty("返回的点金石")
  private Long diamond;

  public String getDiamondYuan(){
    return PriceUtil.formatYuan(diamond);
  }

  @ApiModelProperty("实时库存")
  private Long stock;

  @ApiModelProperty("规格状态")
  private Integer status;

  @ApiModelProperty("当前批次号, 订单中要记录下在结算时使用")
  private String batchNo;
}
