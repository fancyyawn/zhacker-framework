package top.zhacker.sample.retail.online.item.domain.item.exhibit;

import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.ItemPrice;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemAddParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemDeleteParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemUpdateParam;
import top.zhacker.sample.retail.online.item.application.ItemService;
import top.zhacker.sample.retail.online.item.util.BeanUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 会场关联的商品
 *
 * Created by zhacker.
 * Time 2018/1/24 下午9:30
 */
@Data
@Accessors(chain = true)
public class ExhibitionItem implements Serializable {
  
  private Long id;
  @ApiModelProperty("会场ID")
  private Long exhibitionId;
  @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
  private Integer type;
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("商品价格")
  private ItemPrice price;
  
  @ApiModelProperty("设置sku活动相关属性")
  private List<ExhibitionItemSku> skus = new ArrayList<>();
  
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;



  public Long copy(ExhibitionItemRepo repo, Long exhibitionId){
    ExhibitionItem item = BeanUtil.deepCopy(this, ExhibitionItem.class);
    item.setId(null);
    item.setExhibitionId(exhibitionId);
    repo.create(item);
    return item.getId();
  }
  
  public static void create(ExhibitionItemRepo repo, ItemService itemService, ExhibitionItemAddParam param){
    
    param.getItemIds().forEach(itemId->{
      ExhibitionItem item = new ExhibitionItem().setExhibitionId(param.getExhibitionId()).setType(param.getType()).setItemId(itemId);
  
      Item origin = itemService.loadWithSkuAndDetail(item.getItemId());
  
      item.setPrice(origin.getPrice());
      item.setSkus(origin.getSkus().stream().map(s->s.toExhibitionSku(param.getExhibitionId())).collect(Collectors.toList()));
  
      repo.create(item);
    });
  }
  
  public static void delete(ExhibitionItemRepo repo, ExhibitionItemDeleteParam param){
    repo.batchDelete(param);
  }
  
  public static void update(ExhibitionItemRepo repo, ExhibitionItemUpdateParam param){
    ExhibitionItem item = new ExhibitionItem();
    item.setId(param.getId()).setSkus(param.getSkus()).setPrice(param.getPrice());
    repo.update(item);
  }
  
  public static ExhibitionItem load(ExhibitionItemRepo repo, Long exhibitionId, Long itemId) {
    return repo.findByExhibitionIdAndItemId(exhibitionId, itemId);
  }
  
  public static ExhibitionItem load(ExhibitionItemRepo repo, Long id){
    return repo.findById(id);
  }
}
