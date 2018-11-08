package top.zhacker.sample.retail.online.item.domain.price;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Created by zhacker.
 * Time 2018/3/3 下午3:17
 */
public class PriceUtil {
  
  public static String formatYuan(Long price){
    if(price==null){
      return "";
    }
    BigDecimal decimal = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
    return decimal.toString();
  }
}
