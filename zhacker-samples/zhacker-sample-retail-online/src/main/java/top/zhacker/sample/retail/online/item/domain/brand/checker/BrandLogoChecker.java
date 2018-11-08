package top.zhacker.sample.retail.online.item.domain.brand.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Setter;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Checker;
import top.zhacker.sample.retail.online.item.domain.photo.PhotoUrlChecker;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午7:12
 */
@Component
public class BrandLogoChecker implements Checker<String> {
  
  @Autowired
  @Setter
  private PhotoUrlChecker photoUrlChecker;
  
  @Override
  public void check(String logo) {
    if(photoUrlChecker.invalid(logo)){
      throw new BusinessException("品牌Logo的URL不合法");
    }
  }
}
