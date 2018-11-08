package top.zhacker.sample.retail.online.item.domain.item.sku.bill;

import org.springframework.stereotype.Repository;


/**
 * Created by zhacker.
 * Time 2018/1/20 上午11:48
 */
@Repository
public interface StockBillRepo {
  
  void create(StockBill bill);
  
}
