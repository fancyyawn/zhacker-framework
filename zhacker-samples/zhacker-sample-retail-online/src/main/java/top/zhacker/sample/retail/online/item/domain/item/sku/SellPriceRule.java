package top.zhacker.sample.retail.online.item.domain.item.sku;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午10:04
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SellPriceRule {

  /**
   * @see SellType
   */
  @ApiModelProperty("划分类型")
  private Integer type;

  @ApiModelProperty("现金")
  private Long cash;

  @ApiModelProperty("点金石")
  private Long diamond;

  @ApiModelProperty("九洲宝")
  private Long treasure;

  @ApiModelProperty("余额")
  private Long remainder;
}
