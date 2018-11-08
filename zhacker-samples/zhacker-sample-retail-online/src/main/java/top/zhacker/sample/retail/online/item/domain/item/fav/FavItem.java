package top.zhacker.sample.retail.online.item.domain.item.fav;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Transient;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemDeleteParam;
import top.zhacker.sample.retail.online.item.application.ItemService;
import top.zhacker.sample.retail.online.item.util.BeanUtil;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:08
 */
@Data
@Accessors(chain = true)
public class FavItem {
  
  private Long id;
  private Long userId;
  private Long itemId;
  private Long version;
  private Date createdAt;
  private Date updatedAt;
  
  @Transient
  private Item item;
  
  public FavItem loadItem(ItemService itemService){
    item = itemService.loadWithSkuAndDetail(itemId);
    return this;
  }
  
  public static Optional<FavItem> findByItemIdForUser(FavItemRepo repo, Long userId, Long itemId){
    return Optional.ofNullable(repo.findByItemId(userId, itemId));
  }
  
  public static FavItem load(FavItemRepo repo, Long userId, Long itemId){
    return findByItemIdForUser(repo, userId, itemId).orElseThrow(()-> new BusinessException( "收藏的商品不存在"));
  }
  
  public static FavItem from(FavItemCreateParam param){
    return BeanUtil.shadowCopy(param, FavItem.class).setVersion(0L).setCreatedAt(new Date()).setUpdatedAt(new Date());
  }
  
  public static Long create(FavItemRepo repo, FavItemCreateParam param){
    Optional<FavItem> item = findByItemIdForUser(repo, param.getUserId(), param.getItemId());
    if(item.isPresent()){
      return item.get().getId();
    }else {
      return from(param).create(repo).getId();
    }
  }
  
  public FavItem create(FavItemRepo repo){
    repo.create(this);
    return this;
  }
  
  public static void delete(FavItemRepo repo, FavItemDeleteParam param){
    repo.deleteByItemId(param.getUserId(), param.getItemId());
  }
  
}
