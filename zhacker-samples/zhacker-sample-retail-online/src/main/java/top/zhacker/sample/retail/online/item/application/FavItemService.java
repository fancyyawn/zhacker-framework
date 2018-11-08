package top.zhacker.sample.retail.online.item.application;

import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.ItemRepo;
import top.zhacker.sample.retail.online.item.domain.item.fav.FavItem;
import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemDeleteParam;
import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemPageParam;
import top.zhacker.sample.retail.online.item.domain.item.fav.FavItemRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:32
 */
@Service
public class FavItemService {
  
  @Autowired
  private FavItemRepo repo;
  
  @Autowired
  private ItemService itemService;
  
  @Autowired
  private ItemRepo itemRepo;
  
  @Transactional
  public Long create(FavItemCreateParam param) {
    Item.load(itemRepo, param.getItemId());
    return FavItem.create(repo, param);
  }
  
  @Transactional
  public Boolean delete(FavItemDeleteParam param) {
    FavItem.delete(repo, param);
    return Boolean.TRUE;
  }
  
  public Page<FavItem> page(FavItemPageParam param) {
    long count = repo.count(param);
    List<FavItem> list = repo.page(param);
    list.forEach(fav-> fav.loadItem(itemService));
    return new Page<>(count, list);
  }
  
  public Boolean inFav(Long userId, Long itemId){
    return FavItem.findByItemIdForUser(repo, userId, itemId).isPresent();
  }
}
