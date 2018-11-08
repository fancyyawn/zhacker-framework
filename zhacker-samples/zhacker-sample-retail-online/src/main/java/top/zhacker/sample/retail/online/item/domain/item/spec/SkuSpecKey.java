package top.zhacker.sample.retail.online.item.domain.item.spec;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午10:16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SkuSpecKey {
  
  @ApiModelProperty("键ID, 为键在一个商品相关规格列表中的相对位置，从0开始")
  private Long keyId;
  
  @ApiModelProperty("键名称，如规格颜色")
  private String keyName;
}
