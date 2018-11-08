package top.zhacker.sample.retail.online.item.domain.item.search;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/14.
 */
@Data
@Accessors(chain = true)
public class ItemSearch implements Serializable {

  private Long id;
  private String field;
  private String keyword;
  private Long count;

  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

}
