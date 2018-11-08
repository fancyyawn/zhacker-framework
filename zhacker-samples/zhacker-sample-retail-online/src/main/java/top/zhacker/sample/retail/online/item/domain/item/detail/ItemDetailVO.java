package top.zhacker.sample.retail.online.item.domain.item.detail;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午6:50
 */
@Data
@Accessors(chain = true)
public class ItemDetailVO {
  
  @ApiModelProperty("详情ID")
  private Long id;
  
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("详情文字版描述")
  private String description;
  
  @ApiModelProperty("详情图片列表")
  private List<String> photos = new ArrayList<>();
  
  @JsonIgnore
  @ApiModelProperty("版本号")
  private Long version;
  @JsonIgnore
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @JsonIgnore
  @ApiModelProperty("更新时间")
  private Date updatedAt;
}
