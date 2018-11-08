package top.zhacker.sample.retail.online.item.domain.item.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import lombok.Setter;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.application.BrandService;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午6:41
 */
@Component
public class ItemBrandChecker implements Checker<Item> {
  
  @Autowired
  @Setter
  private BrandService brandService;
  
  
  @Override
  public void check(Item item) {
    if(item.getBrandId()!=null) {
      Optional.ofNullable(brandService.get(item.getBrandId()))
          .orElseThrow(() -> new BusinessException("商品单位不存在"));
    }
  }
}
