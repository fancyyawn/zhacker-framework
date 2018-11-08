package top.zhacker.sample.retail.online.item.domain.exhibition;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionQueryParam;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午8:50
 */
@Repository
public interface ExhibitionRepo {
  
  void create(Exhibition exhibition);
  
  int update(Exhibition exhibition);
  
  Exhibition findById(@Param("id") Long id);
  
  List<Exhibition> paging(ExhibitionQueryParam queryParam);
  
  long count(ExhibitionQueryParam queryParam);
  
  int batchStart();
  
  int batchEnd();
}
