package top.zhacker.sample.retail.online.item.domain.item.sku.param;

import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午9:13
 */
@Data
@Accessors(chain = true)
public class ItemSkuUpdateParam {
  
  @ApiModelProperty("规格ID")
  @NotNull
  private Long id = 0L;
  
  @ApiModelProperty("商品ID")
  @NotNull
  private Long itemId;
  
  @ApiModelProperty("规格定义列表")
  @NotNull
  @NotEmpty
  private List<SkuSpecKeyValue> specs;
  
}
