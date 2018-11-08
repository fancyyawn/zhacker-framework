package top.zhacker.sample.retail.online.item.application.brand.param;

import top.zhacker.sample.retail.online.item.common.PageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:21
 */
@ApiModel(description = "品牌分页查询")
@Data
@Accessors(chain = true)
public class BrandQueryParam extends PageParam {
  
  @ApiModelProperty("品牌名称，模糊匹配")
  private String name;

  @ApiModelProperty("是否隐藏")
  private Boolean hidden;
}
