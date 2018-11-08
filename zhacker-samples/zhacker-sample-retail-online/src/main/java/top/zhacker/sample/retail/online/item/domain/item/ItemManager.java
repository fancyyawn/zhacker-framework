package top.zhacker.sample.retail.online.item.domain.item;

import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetailRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午7:57
 */
@Component
public class ItemManager {
  
  @Autowired
  @Setter
  @Getter
  private ItemRepo itemRepo;
  
  @Autowired
  @Setter
  @Getter
  private ItemSkuRepo itemSkuRepo;
  
  @Autowired
  @Setter
  @Getter
  private ItemDetailRepo itemDetailRepo;
  
  @Autowired
  @Setter
  @Getter
  private PurchaseOrderRepo purchaseOrderRepo;
  
  @Transactional
  public Item save(Item item){
    itemRepo.create(item);
    item.getDetail().save(itemDetailRepo, item.getId());
    item.getSkus().forEach(sku-> sku.save(itemSkuRepo, item.getId()));
    return item;
  }
  
}
