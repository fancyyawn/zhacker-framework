package top.zhacker.sample.retail.online.item.domain.item.exhibit;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemDeleteParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemQueryParam;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午5:48
 */
@Repository
public interface ExhibitionItemRepo {
  
  void batchDelete(ExhibitionItemDeleteParam param);
  
  void create(ExhibitionItem item);
  
  int update(ExhibitionItem item);
  
  ExhibitionItem findById(@Param("id") Long id);
  
  List<ExhibitionItem> findByExhibitionId(@Param("exhibitionId") Long exhibitionId);
  
  ExhibitionItem findByExhibitionIdAndItemId(@Param("exhibitionId") Long exhibitionId, @Param("itemId") Long itemId);
  
  List<ExhibitionItem> findByTypeAndItemId(@Param("type") Integer type, @Param("itemId") Long itemId);

  List<ExhibitionItem> findBy(ExhibitionItemQueryParam param);
}
