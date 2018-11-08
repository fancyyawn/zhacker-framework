package top.zhacker.sample.retail.online.item.domain.photo;

import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Setter;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午7:03
 */
@Component
public class PhotoUrlChecker {
  
  @Value("${file-system.oss.outside-endpoint:aliyuncs.com}")
  @Setter
  private String oss;
  
  public boolean valid(String url){
    if(Strings.isNullOrEmpty(url)){
      return false;
    }
    if(url.contains(oss)){
      return true;
    }else{
      return false;
    }
  }
  
  public boolean invalid(String url){
    return ! valid(url);
  }
}
