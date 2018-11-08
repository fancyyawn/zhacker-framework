package top.zhacker.sample.retail.online.item.application.exhibition.param;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ExhibitionCopyParam implements Serializable {

  @ApiModelProperty("活动ID")
  private Long exhibitionId;
  @ApiModelProperty("开始时间")
  private Date startAt;
  @ApiModelProperty("截止时间")
  private Date endAt;
}
