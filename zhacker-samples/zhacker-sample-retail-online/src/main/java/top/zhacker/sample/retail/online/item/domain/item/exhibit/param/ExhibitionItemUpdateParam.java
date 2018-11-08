package top.zhacker.sample.retail.online.item.domain.item.exhibit.param;

import top.zhacker.sample.retail.online.item.domain.item.ItemPrice;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemSku;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午3:46
 */
@Data
@Accessors(chain = true)
public class ExhibitionItemUpdateParam {
  
  @ApiModelProperty("活动商品ID")
  private Long id;
  
  @ApiModelProperty("商品价格")
  private ItemPrice price;
  
  @ApiModelProperty("设置sku活动相关属性")
  private List<ExhibitionItemSku> skus = new ArrayList<>();
  
}
