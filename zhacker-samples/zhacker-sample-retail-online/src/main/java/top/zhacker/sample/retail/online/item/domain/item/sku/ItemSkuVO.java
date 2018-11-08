package top.zhacker.sample.retail.online.item.domain.item.sku;

import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午6:51
 */
@Data
@Accessors(chain = true)
public class ItemSkuVO {
  
  @ApiModelProperty("规格ID")
  private Long id;
  
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("规格定义列表")
  private List<SkuSpecKeyValue> specs;
  
  @ApiModelProperty("原价")
  private Long originPrice;
  
  @ApiModelProperty("销售价")
  private Long sellPrice;
  
  @ApiModelProperty("销售价划分规则")
  private SellPriceRule sellPriceRule;
  
  @ApiModelProperty("点金石")
  private Long diamond;
  
  @ApiModelProperty("实时库存")
  private Long stock;
  
  @ApiModelProperty("当前批次号, 订单中要记录下在结算时使用")
  private String batchNo;
  
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  
}
