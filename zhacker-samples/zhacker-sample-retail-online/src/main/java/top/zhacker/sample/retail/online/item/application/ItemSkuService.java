package top.zhacker.sample.retail.online.item.application;

import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.ItemPrice;
import top.zhacker.sample.retail.online.item.domain.item.ItemRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSku;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockBillRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeBatchParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateSellParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateStatusParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;


/**
 * Created by zhacker.
 * Time 2018/1/20 下午1:29
 */
@Service
public class ItemSkuService {
  
  @Autowired
  private ItemSkuRepo itemSkuRepo;
  
  @Autowired
  private StockBillRepo stockBillRepo;
  
  @Autowired
  private ItemRepo itemRepo;

  
  
  /**
   * 可用于查询库存是否存在
   * @param ids
   * @return
   */
  public List<ItemSku> findByIds(List<Long> ids){
    return ItemSku.findByIds(itemSkuRepo, ids);
  }
  
  
  /**
   * 变更库存记录
   *
   * @param param
   * @return
   */
  @Transactional
  public Boolean changeStock(StockChangeBatchParam param){
    return ItemSku.changeStock(itemSkuRepo, stockBillRepo, param);
  }

  @Transactional
  public Boolean clearStockWhenOffline(Long skuId, Long userId){
    return ItemSku.clearStockWhenOffline(itemSkuRepo, stockBillRepo, skuId, userId);
  }
  
  @Transactional
  public Boolean updateStatus(ItemSkuUpdateStatusParam param){
     ItemSku.load(itemSkuRepo, param.getId()).updateStatus(itemSkuRepo, param.getStatus());
     return Boolean.TRUE;
  }
  
  @Transactional
  public Boolean updateSellInfo(ItemSkuUpdateSellParam param){
    ItemSku sku = ItemSku.load(itemSkuRepo, param.getId()).updateSellInfo(itemSkuRepo, param);
    
    Item item = Item.load(itemRepo, sku.getItemId()).loadSkus(itemSkuRepo);
    
    //每次更新销售价时，都判断是否更新商品售价（为所有sku的最低价）
    ItemSku minSku = item.getSkus().stream().filter(s-> !Objects.equals(s.getSellPrice(), 0L))
        .min(Comparator.comparing(ItemSku::getSellPrice))
        .orElse(sku);

    item.updatePrice(itemRepo, new ItemPrice(minSku.getOriginPrice(), minSku.getSellPrice(), minSku.getDiamond(), minSku.getId()));

    return Boolean.TRUE;
  }
  
}
