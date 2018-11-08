package top.zhacker.sample.retail.online.item.application;

import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockBillRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrder;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderPageParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午4:41
 */
@Service
@Slf4j
public class PurchaseOrderService {
  
  @Autowired
  private PurchaseOrderRepo repo;
  
  @Autowired
  private ItemSkuRepo itemSkuRepo;
  
  @Autowired
  private StockBillRepo billRepo;
  
  public Long count(PurchaseOrderPageParam pageParam){
    return repo.count(pageParam);
  }
  
  public List<PurchaseOrder> page(PurchaseOrderPageParam pageParam){
    return repo.page(pageParam);
  }
  
  @Transactional
  public PurchaseOrder create(PurchaseOrderCreateParam param){
    return PurchaseOrder
        .create(repo, itemSkuRepo, billRepo, param);
  }
}
