package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeBatchParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateSellParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateStatusParam;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.ItemSkuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/2/11 下午9:59
 */
@RestController
@Slf4j
@RequestMapping("/v1/sku")
@ParamLog
public class ItemSkuApi {
  
  @Autowired
  private ItemSkuService itemSkuService;
  
  @PostMapping("/sell-info")
  public Result<Boolean> updateSellInfo(@RequestBody ItemSkuUpdateSellParam param){
    return Result.ok(itemSkuService.updateSellInfo(param));
  }
  
  @PostMapping("/status")
  public Result<Boolean> updateStatus(@RequestBody ItemSkuUpdateStatusParam param){
    return Result.ok(itemSkuService.updateStatus(param));
  }

  @PostMapping("/{id}/clear-stock")
  public Result<Boolean> clearStockWhenOffline(@PathVariable("id") Long skuId){
    return Result.ok(itemSkuService.clearStockWhenOffline(skuId, 0L));
  }
  
  @PostMapping("/change-stock")
  public Result<Boolean> changeStock(@RequestBody StockChangeBatchParam param){
    return Result.ok(itemSkuService.changeStock(param));
  }
}
