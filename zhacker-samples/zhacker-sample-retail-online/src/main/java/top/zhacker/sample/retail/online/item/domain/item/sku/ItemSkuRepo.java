package top.zhacker.sample.retail.online.item.domain.item.sku;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午5:53
 */
@Repository
public interface ItemSkuRepo {
  
  void create(ItemSku itemSku);
  
  List<ItemSku> findByIds(@Param("ids") List<Long> ids);
  
  int update(ItemSku itemSku);
  
  ItemSku findById(@Param("id") Long id);
  
  List<ItemSku> findByItemId(@Param("itemId") Long itemId);
}
