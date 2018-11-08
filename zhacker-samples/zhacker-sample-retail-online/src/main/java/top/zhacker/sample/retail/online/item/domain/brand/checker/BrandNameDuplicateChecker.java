package top.zhacker.sample.retail.online.item.domain.brand.checker;

import com.google.common.base.Strings;

import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.domain.brand.Brand;
import top.zhacker.sample.retail.online.item.domain.brand.BrandRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午7:16
 */
@Component
@Slf4j
public class BrandNameDuplicateChecker implements Checker<Brand>{
  
  @Autowired
  @Setter
  private BrandRepo brandRepo;
  
  @Override
  public void check(Brand brand) {
    if(Strings.isNullOrEmpty(brand.getName())){
      return;
    }
    Brand exist = brandRepo.findByName(brand.getName());
    if(exist!=null && ! Objects.equals(brand.getId(), exist.getId())){
      throw new BusinessException("品牌名称已经被使用");
    }
  }
}
