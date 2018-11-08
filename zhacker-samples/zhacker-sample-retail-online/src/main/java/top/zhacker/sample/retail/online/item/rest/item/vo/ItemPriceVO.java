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
public class ItemPriceVO implements Serializable {

  @ApiModelProperty("原价")
  private Long originPrice = 0L;

  public String getOriginPriceYuan(){
    return PriceUtil.formatYuan(originPrice);
  }

  @ApiModelProperty("销售价")
  private Long sellPrice = 0L;

  public String getSellPriceYuan(){
    return PriceUtil.formatYuan(sellPrice);
  }

  @ApiModelProperty("点金石")
  private Long diamond = 0L;

  public String getDiamondYuan(){
    return PriceUtil.formatYuan(diamond);
  }
}
