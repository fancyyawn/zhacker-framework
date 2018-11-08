package top.zhacker.sample.retail.online.item.domain.item;

import top.zhacker.sample.retail.online.item.domain.item.param.ItemPageParam;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午6:13
 */
@Repository
public interface ItemRepo {
  
  void create(Item item);
  
  int update(Item item);
  
  Item findById(Long id);
  
  List<Item> page(ItemPageParam param);
  
  long count(ItemPageParam param);
}
