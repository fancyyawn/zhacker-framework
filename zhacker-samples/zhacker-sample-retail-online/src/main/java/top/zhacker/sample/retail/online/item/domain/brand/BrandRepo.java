package top.zhacker.sample.retail.online.item.domain.brand;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import top.zhacker.sample.retail.online.item.application.brand.param.BrandQueryParam;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:19
 */
@Repository
public interface BrandRepo {
  
  void create(Brand brand);
  
  int update(Brand brand);
  
  int delete(@Param("id") Long id);
  
  long count(BrandQueryParam param);
  
  List<Brand> page(BrandQueryParam param);
  
  Brand findById(@Param("id") Long id);
  
  Brand findByName(@Param("name") String name);
  
  List<Brand> findByNamePart(@Param("namePart") String namePart);
}
