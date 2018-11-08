package top.zhacker.sample.retail.online.item.domain.item.spec;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午10:18
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SkuSpecValue{
  
  @ApiModelProperty("值ID, 为值在一个商品某个规格值列表中的相对位置，从0开始")
  private Long valueId;
  
  @ApiModelProperty("值名称，如颜色规格下的红色")
  private String valueName;
}
