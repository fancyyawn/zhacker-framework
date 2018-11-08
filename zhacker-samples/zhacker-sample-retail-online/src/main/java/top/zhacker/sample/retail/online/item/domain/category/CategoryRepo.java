package top.zhacker.sample.retail.online.item.domain.category;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:51
 */
@Repository
public interface CategoryRepo {
  
  void create(Category category);
  
  int update(Category category);

  Category findByParentIdAndName(@Param("parentId") Long parentId, @Param("name") String name);
  
  List<Category> findAll();

  List<Category> findVisible();
  
  Category findById(Long id);
  
}
