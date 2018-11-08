package top.zhacker.sample.retail.online.item.domain.item.sku.param;

import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午7:54
 */
@Data
@Accessors(chain = true)
public class ItemSkuCreateParam {
  
  @ApiModelProperty("规格定义列表")
  private List<SkuSpecKeyValue> specs;
}
