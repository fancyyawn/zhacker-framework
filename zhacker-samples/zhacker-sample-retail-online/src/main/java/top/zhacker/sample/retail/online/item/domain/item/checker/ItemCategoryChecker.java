package top.zhacker.sample.retail.online.item.domain.item.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import lombok.Setter;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.application.CategoryService;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午6:37
 */
@Component
public class ItemCategoryChecker implements Checker<Item> {
  
  @Autowired
  @Setter
  private CategoryService categoryService;
  
  @Override
  public void check(Item item) {
    if(item.getCategoryId()!=null) {
      Optional.ofNullable(categoryService.findById(item.getCategoryId()))
          .orElseThrow(() -> new BusinessException("商品分类不合法"));
    }
  }
}
