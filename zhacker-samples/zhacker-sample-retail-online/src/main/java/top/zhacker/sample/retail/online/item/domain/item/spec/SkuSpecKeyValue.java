package top.zhacker.sample.retail.online.item.domain.item.spec;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午1:40
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SkuSpecKeyValue {
  
  @ApiModelProperty("规格键")
  private SkuSpecKey key;
  
  @ApiModelProperty("规格值")
  private SkuSpecValue value;
}
