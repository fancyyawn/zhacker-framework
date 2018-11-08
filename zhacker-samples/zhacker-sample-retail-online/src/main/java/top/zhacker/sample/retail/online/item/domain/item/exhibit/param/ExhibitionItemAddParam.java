package top.zhacker.sample.retail.online.item.domain.item.exhibit.param;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/24 下午10:22
 */
@Data
@Accessors(chain = true)
public class ExhibitionItemAddParam {
  
  @ApiModelProperty("会场ID")
  private Long exhibitionId;
  
  @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
  private Integer type;
  
  @ApiModelProperty("商品ID列表")
  private List<Long> itemIds = new ArrayList<>();
  
}
