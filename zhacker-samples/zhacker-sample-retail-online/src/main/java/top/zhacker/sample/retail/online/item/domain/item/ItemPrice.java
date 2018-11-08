package top.zhacker.sample.retail.online.item.domain.item;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/24 下午8:32
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ItemPrice implements Serializable {

  @ApiModelProperty("原价")
  private Long originPrice = 0L;

  @ApiModelProperty("销售价")
  private Long sellPrice = 0L;

  @ApiModelProperty("点金石")
  private Long diamond = 0L;

  private Long skuId;
}
