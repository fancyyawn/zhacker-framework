package top.zhacker.sample.retail.online.item.application.brand.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:17
 */
@ApiModel(value = "BrandCreateParam", description = "品牌创建对象")
@Data
@Accessors(chain = true)
public class BrandCreateParam {

  @ApiModelProperty(value = "品牌名称", required = true)
  @NotNull(message = "品牌名称不能为空")
  private String name;

  @ApiModelProperty(value = "品牌logo，必须为合法的url", required = true)
  @NotNull(message = "品牌logo不能为空")
  private String logo;

  @ApiModelProperty("排序分值")
  private Long score = 0L;

  @ApiModelProperty("是否隐藏")
  private Boolean hidden = false;
}
