package top.zhacker.sample.retail.online.item.domain.item.sku.purchase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by zhacker.
 * Time 2018/2/7 下午9:45
 */
public class BatchNoBuilder {

  public static String build(Long skuId){
    return String.format("%s%014d", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),skuId);
  }
}
