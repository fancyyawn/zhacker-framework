package top.zhacker.sample.retail.online.item.domain.item.sku.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.ItemRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.BizType;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrder;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderRepo;


/**
 * Created by zhacker.
 * Time 2018/2/13 下午5:31
 */
@Component
@Slf4j
public class StockChangeEventListener {
  
  @Autowired
  private ItemRepo itemRepo;
  
  @Autowired
  private PurchaseOrderRepo purchaseOrderRepo;
  
  
  @EventListener
  public void changeItemSales(StockChangeEvent event){
    if(Objects.equals(event.getBizType(), BizType.SELL_OUT.getValue())
        ||Objects.equals(event.getBizType(), BizType.ORDER_CANCEL_IN.getValue())
        ||Objects.equals(event.getBizType(), BizType.ORDER_RETURN_IN.getValue())){
      Item.updateSales(itemRepo, event.getItemId(), event.getQuantity() * -1);
    }
  }
  
  @EventListener
  public void changeSkuSellCount(StockChangeEvent event){
    if(Objects.equals(event.getBizType(), BizType.SELL_OUT.getValue())
        ||Objects.equals(event.getBizType(), BizType.ORDER_CANCEL_IN.getValue())
        || Objects.equals(event.getBizType(), BizType.ORDER_RETURN_IN.getValue())){
      PurchaseOrder.updateSales(purchaseOrderRepo, event.getSkuId(), event.getQuantity()* -1);
    }
  }
  
}
