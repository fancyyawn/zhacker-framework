package top.zhacker.sample.retail.online.item.domain.item.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Setter;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.photo.PhotoUrlChecker;


/**
 * Created by zhacker.
 * Time 2018/1/19 下午10:06
 */
@Component
public class ItemPhotosChecker implements Checker<Item> {
  
  @Setter
  @Autowired
  private PhotoUrlChecker photoUrlChecker;
  
  @Override
  public void check(Item item) {
    if(item.getPhotos().stream().anyMatch(photoUrlChecker::invalid)){
      throw new BusinessException("商品主图图片URL不合法");
    }
  }
}
