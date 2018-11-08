package top.zhacker.sample.retail.online.item.application.exhibition.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/13 下午2:16
 */
@Data
@Accessors(chain = true)
public class ExhibitionUpdateStatusParam {
  
  private Long id;
  
  @ApiModelProperty("展会的状态：0未激活，1正常，-1过期")
  private Integer status;
}
