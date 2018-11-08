package top.zhacker.sample.retail.online.item.application.category.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.sample.retail.online.item.domain.category.Category;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:46
 */
@Data
@Accessors(chain = true)
public class CategoryCreateParam {
  
  @NotNull(message = "父分类ID不能为空")
  @ApiModelProperty("父分类ID，根分类时为0")
  private Long parentId = Category.ROOT_ID;
  
  @NotNull(message = "分类名称不能为空")
  @ApiModelProperty("分类名称")
  private String name;
  
  @NotNull(message = "分类图片不能为空")
  @ApiModelProperty("分类图片，为合法图片URL")
  private String banner;
  
  @ApiModelProperty("是否显示分类图片，默认是")
  private Boolean showBanner = true;

  @ApiModelProperty("排序分值")
  private Long score=0L;

  @ApiModelProperty("是否隐藏")
  private Boolean hidden = false;
}
