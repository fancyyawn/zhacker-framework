package top.zhacker.sample.retail.online.item.domain.item.detail.param;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午7:54
 */
@Data
@Accessors(chain = true)
public class ItemDetailCreateParam{
  
  @ApiModelProperty("详情文字版描述")
  private String description;
  
  @ApiModelProperty("详情图片列表")
  private List<String> photos = new ArrayList<>();
  
}
