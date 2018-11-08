package top.zhacker.sample.retail.online.item.domain.item.snapshot;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import com.alibaba.fastjson.JSON;

import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.domain.item.ItemVO;
import top.zhacker.sample.retail.online.item.application.ItemService;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午4:54
 */
@Data
@Accessors(chain = true)
public class ItemSnapshot implements Serializable {
  
  private Long id;
  
  @ApiModelProperty("商品ID")
  private Long itemId;
  
  @ApiModelProperty("商品所有信息的Hash值")
  @Setter(AccessLevel.PRIVATE)
  private String itemMd5;
  
  @ApiModelProperty("商品所有信息，包含sku、详情等")
  private ItemVO snapshot;
  
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  
  private static final HashFunction md5 = Hashing.md5();
  
  public ItemSnapshot computeItemMd5(){
    if(snapshot!=null) {
      itemMd5 = md5.newHasher().putString(JSON.toJSONString(snapshot), Charsets.UTF_8).hash().toString();
    }
    return this;
  }
  
  public static Long create(ItemSnapshotRepo repo, ItemService itemService, ItemSnapshotCreateParam param){
    ItemSnapshot snapshot = param.build(itemService);
    Long exist = repo.findByItemIdAndItemMd5(snapshot.getItemId(), snapshot.getItemMd5());
    if(exist!=null){
      return exist;
    }
    repo.create(snapshot);
    return snapshot.getId();
  }
  
  public static ItemSnapshot load(ItemSnapshotRepo repo, Long id){
    return Optional.ofNullable(repo.findById(id)).orElseThrow(()->new BusinessException("商品快照不存在"));
  }
}
