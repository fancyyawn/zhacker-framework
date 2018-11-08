package top.zhacker.sample.retail.online.item.application.brand.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:18
 */
@Data
@Accessors(chain = true)
public class BrandDeleteParam {
  
  @ApiModelProperty("品牌ID")
  @NotNull(message = "ID不能为空")
  private Long id;
}
