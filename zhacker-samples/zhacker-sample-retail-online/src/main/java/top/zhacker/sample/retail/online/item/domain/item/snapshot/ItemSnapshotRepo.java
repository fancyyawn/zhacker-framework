package top.zhacker.sample.retail.online.item.domain.item.snapshot;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午5:03
 */
@Repository
public interface ItemSnapshotRepo {
  
  void create(ItemSnapshot snapshot);
  
  ItemSnapshot findById(@Param("id") Long id);
  
  Long findByItemIdAndItemMd5(@Param("itemId") Long itemId, @Param("itemMd5") String itemMd5);
}
