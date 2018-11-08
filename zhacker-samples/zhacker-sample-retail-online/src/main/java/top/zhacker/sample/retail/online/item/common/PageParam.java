package top.zhacker.sample.retail.online.item.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Created by zhacker.
 * Time 2018/1/12 下午8:04
 */
@Data
public class PageParam {
  
  @ApiModelProperty("分页号，从1开始")
  private Integer pageNo = 1;
  
  @ApiModelProperty("分页大小")
  private Integer pageSize = 20;
  
  public Integer getLimit(){
    return pageSize;
  }
  
  public Integer getOffset(){
    return (pageNo-1)*pageSize;
  }
}
