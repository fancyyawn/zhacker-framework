package top.zhacker.sample.retail.online.item.domain.item.detail;

import org.springframework.stereotype.Repository;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午7:47
 */
@Repository
public interface ItemDetailRepo {
  
  void create(ItemDetail detail);
  
  int update(ItemDetail detail);
  
  ItemDetail findByItemId(Long itemId);
}
