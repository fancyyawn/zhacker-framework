package top.zhacker.sample.retail.online.item.application.brand.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:20
 */
@Data
@Accessors(chain = true)
public class BrandUpdateParam {
  
  @ApiModelProperty(value = "品牌ID", required = true)
  @NotNull(message = "ID不能为空")
  private Long id;
  
  @ApiModelProperty(value = "品牌名称", required = true)
//  @NotNull(message = "品牌名称不能为空")
  private String name;
  
  @ApiModelProperty(value = "品牌logo", required = true)
//  @NotNull(message = "品牌logo不能为空")
  private String logo;

  @ApiModelProperty("排序分值")
  private Long score;

  @ApiModelProperty("是否隐藏")
  private Boolean hidden;
}
