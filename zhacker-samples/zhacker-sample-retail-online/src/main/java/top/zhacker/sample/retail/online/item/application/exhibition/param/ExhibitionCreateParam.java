package top.zhacker.sample.retail.online.item.application.exhibition.param;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.sample.retail.online.item.domain.exhibition.ExhibitionType;


/**
 * Created by zhacker.
 * Time 2018/1/24 下午9:28
 */
@Data
@Accessors(chain = true)
public class ExhibitionCreateParam {
  
  @ApiModelProperty("会场名称")
  private String name;
  @ApiModelProperty("描述")
  private String description;
  @ApiModelProperty("主图")
  private String mainPhoto;
  @ApiModelProperty("导航图，用于首页")
  private String navigatePhoto;
  @ApiModelProperty("开始时间")
  private Date startAt;
  @ApiModelProperty("截止时间")
  private Date endAt;
  /**
   * @see ExhibitionType
   */
  @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
  private Integer type;

  @ApiModelProperty("排序分值")
  private Long score = 0L;
}
