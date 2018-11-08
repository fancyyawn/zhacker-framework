package top.zhacker.sample.retail.online.item.domain.item.detail.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

import lombok.Setter;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.common.Operation;
import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetail;
import top.zhacker.sample.retail.online.item.domain.photo.PhotoUrlChecker;


/**
 * Created by zhacker.
 * Time 2018/1/19 下午10:06
 */
@Component
public class ItemDetailPhotosChecker implements Checker<ItemDetail> {
  
  @Setter
  @Autowired
  private PhotoUrlChecker photoUrlChecker;
  
  @Override
  public void check(ItemDetail detail) {
    if(detail.getPhotos().stream().anyMatch(photoUrlChecker::invalid)){
      throw new BusinessException("商品详情图片URL不合法");
    }
  }
  
  
  @Override
  public EnumSet<Operation> support() {
    return EnumSet.allOf(Operation.class);
  }
}
