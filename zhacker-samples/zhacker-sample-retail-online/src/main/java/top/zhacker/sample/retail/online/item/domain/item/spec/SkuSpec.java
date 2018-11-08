package top.zhacker.sample.retail.online.item.domain.item.spec;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午5:45
 */
@Data
@Accessors(chain = true)
public class SkuSpec {
  @ApiModelProperty("规格键")
  private SkuSpecKey key;
  @ApiModelProperty("规格值列表")
  private List<SkuSpecValue> values = new ArrayList<>();
}
