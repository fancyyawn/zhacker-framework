package top.zhacker.sample.retail.online.item.domain.item.fav;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.sample.retail.online.item.domain.item.fav.param.FavItemPageParam;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:13
 */
@Repository
public interface FavItemRepo {
  
  void create(FavItem item);
  
  int deleteByItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);
  
  FavItem findByItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);
  
  List<FavItem> page(FavItemPageParam param);
  
  long count(FavItemPageParam param);
}
