package top.zhacker.sample.retail.online.item.domain.item.sku.bill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/20 上午11:42
 */
@Data
@Accessors(chain = true)
public class StockChangeSkuParam {
  
  @ApiModelProperty("ItemSku的id")
  private Long skuId;
  
  @ApiModelProperty("数量 可正可负")
  private Long quantity;
  
  @ApiModelProperty("幂等号码")
  private String idempotentNo;
  
}
