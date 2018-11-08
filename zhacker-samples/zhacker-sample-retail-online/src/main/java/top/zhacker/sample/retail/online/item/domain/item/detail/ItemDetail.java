package top.zhacker.sample.retail.online.item.domain.item.detail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import top.zhacker.sample.retail.online.item.common.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.zhacker.sample.retail.online.item.domain.item.detail.param.ItemDetailUpdateParam;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午10:08
 */
@Data
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class ItemDetail extends BaseEntity<ItemDetail> {
  
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
  
  
  public static ItemDetail update(ItemDetailRepo repo, ItemDetailUpdateParam param){
    ItemDetail detail = repo.findByItemId(param.getItemId());
    detail.setDescription(param.getDesc()).setPhotos(param.getPhotos());
    repo.update(detail);
    return detail;
  }
  
  public ItemDetail save(ItemDetailRepo repo, Long itemId){
    this.itemId = itemId;
    repo.create(this);
    return this;
  }
}
