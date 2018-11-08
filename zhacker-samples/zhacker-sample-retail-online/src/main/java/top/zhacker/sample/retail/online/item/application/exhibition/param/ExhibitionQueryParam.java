package top.zhacker.sample.retail.online.item.application.exhibition.param;

import top.zhacker.sample.retail.online.item.common.PageParam;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.sample.retail.online.item.domain.exhibition.ExhibitionStatus;
import top.zhacker.sample.retail.online.item.domain.exhibition.ExhibitionType;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午4:13
 */
@Data
@Accessors(chain = true)
public class ExhibitionQueryParam extends PageParam {
  
  private Long id;
  
  @ApiModelProperty("会场名称")
  private String name;
  
  /**
   * @see ExhibitionType
   */
  @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
  private Integer type;
  
  /**
   * @see ExhibitionStatus
   */
  @ApiModelProperty("展会的状态：0未激活，1正常，-1过期")
  private List<Integer> statuses = new ArrayList<>();
  
  
}
