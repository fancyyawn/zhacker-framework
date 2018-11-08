package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrder;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.PurchaseOrderPageParam;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.PurchaseOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午4:40
 */
@RestController
@Slf4j
@RequestMapping("/v1/purchase-order")
@ParamLog
public class PurchaseOrderApi {

  @Autowired
  private PurchaseOrderService purchaseOrderService;
  

  @GetMapping("/count")
  public Result<Long> count(PurchaseOrderPageParam pageParam){
    return Result.ok(purchaseOrderService.count(pageParam));
  }

  @GetMapping("/list")
  public Result<List<PurchaseOrder>> list(PurchaseOrderPageParam pageParam){
    return Result.ok(purchaseOrderService.page(pageParam));
  }

  @PostMapping
  public Result<Long> create(@RequestBody PurchaseOrderCreateParam param){
//      param.setVendorId(UserContext.getLoginUserId());
    return Result.ok(purchaseOrderService.create(param).getId());
  }
}
