package top.zhacker.sample.retail.online.item.rest.item.vo;

import top.zhacker.sample.retail.online.item.domain.price.PriceUtil;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class SellPriceRuleVO implements Serializable {

  @ApiModelProperty("划分类型")
  private Integer type;

  @ApiModelProperty("现金")
  private Long cash;

  public String getCashYuan(){
    return PriceUtil.formatYuan(cash);
  }

  @ApiModelProperty("点金石")
  private Long diamond;

  public String getDiamondYuan(){
    return PriceUtil.formatYuan(diamond);
  }

  @ApiModelProperty("九洲宝")
  private Long treasure;

  public String getTreasureYuan() {
    return PriceUtil.formatYuan(treasure);
  }

  @ApiModelProperty("余额")
  private Long remainder;

  public String getRemainderYuan(){
    return PriceUtil.formatYuan(remainder);
  }
}
