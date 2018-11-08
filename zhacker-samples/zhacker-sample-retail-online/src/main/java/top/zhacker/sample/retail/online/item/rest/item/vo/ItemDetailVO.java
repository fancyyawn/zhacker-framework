package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ItemDetailVO implements Serializable {

  @ApiModelProperty("详情文字版描述")
  private String description;

  @ApiModelProperty("详情图片列表")
  private List<String> photos = new ArrayList<>();

}
