package top.zhacker.sample.retail.online.item.domain.item.sku.purchase;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/2/10 上午8:55
 */
@Repository
public interface PurchaseOrderRepo {
  
  void create(PurchaseOrder order);
  
  int update(PurchaseOrder order);
  
  PurchaseOrder findByBatchNo(String batchNo);

  PurchaseOrder findLatestBySkuId(Long skuId);
  
  List<PurchaseOrder> page(PurchaseOrderPageParam param);
  
  long count(PurchaseOrderPageParam param);
}
