package top.zhacker.sample.retail.online.item.application.category.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:48
 */
@Data
@Accessors(chain = true)
public class CategoryUpdateParam {
  
  @ApiModelProperty("分类ID")
  private Long id;
  @ApiModelProperty("分类图片，为合法图片URL")
  private String name;
  @ApiModelProperty("分类图片，为合法图片URL")
  private String banner;
  @ApiModelProperty("是否显示分类图片")
  private Boolean showBanner;
  @ApiModelProperty("排序分值")
  private Long score;
  @ApiModelProperty("是否隐藏")
  private Boolean hidden;
}
