package top.zhacker.sample.retail.online.item.domain.item.search;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhanghecheng on 2018/3/14.
 */
@Repository
public interface ItemSearchRepo {

  void create(ItemSearch search);

  void update(ItemSearch search);

  ItemSearch findByFieldAndKeyWord(@Param("field") String field, @Param("keyword") String keyword);

  List<ItemSearch> page(ItemSearchPageParam param);

  long count(ItemSearchPageParam param);
}
