package top.zhacker.sample.retail.online.item.domain.item.sku.purchase;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.domain.item.sku.BizType;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSku;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockBillRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeBatchParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeSkuParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdatePurchaseParam;
import top.zhacker.sample.retail.online.item.util.BeanUtil;


/**
 * Created by zhacker.
 * Time 2018/2/7 下午9:41
 */
@Data
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class PurchaseOrder {

  private Long id;

  @ApiModelProperty("供应商ID")
  private Long vendorId;

  @ApiModelProperty("商品ID")
  private Long itemId;

  @ApiModelProperty("规格ID")
  private Long skuId;

  /**
   * @see BatchNoBuilder#build(Long)
   */
  @ApiModelProperty("批次号")
  private String batchNo;

  @ApiModelProperty("成本价")
  private Long costPrice;

  @ApiModelProperty("结算价")
  private Long settlePrice;

  @ApiModelProperty("进货数量")
  private Long quantity;

  @ApiModelProperty("销售统计量")
  private Long sellCount;

  @ApiModelProperty("已经结算量")
  private Long settleCount;

  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

  public Long getSettleTotal(){
    return Optional.ofNullable(settlePrice).orElse(0L) * Optional.ofNullable(sellCount).orElse(0L);
  }

  public static PurchaseOrder create(PurchaseOrderRepo repo, ItemSkuRepo itemSkuRepo, StockBillRepo billRepo, PurchaseOrderCreateParam param){

    ItemSku sku = ItemSku.load(itemSkuRepo, param.getSkuId());
    if(sku.getStock()!=0){ //只有当库存为0时，才能进行进货操作
      throw new BusinessException("sku未售完");
    }

    PurchaseOrder order = BeanUtil.deepCopy(param, PurchaseOrder.class);

    order.setBatchNo(BatchNoBuilder.build(param.getSkuId()));
    order.setSellCount(0L);
    order.setSettleCount(0L);
    order.setVersion(0L);
    // add by Shangdu Lin - 20180301
    order.setVendorId(param.getVendorId());
    order.setItemId(param.getItemId());
    order.setSkuId(param.getSkuId());
    order.setCostPrice(param.getCostPrice());
    order.setSettlePrice(param.getSettlePrice());
    order.setQuantity(param.getQuantity());
    order.setCreatedAt(new Date());
    order.setUpdatedAt(new Date());

    repo.create(order);

    ItemSkuUpdatePurchaseParam purchaseParam = new ItemSkuUpdatePurchaseParam()
        .setId(param.getSkuId())
        .setCostPrice(param.getCostPrice())
        .setSettlePrice(param.getSettlePrice())
        .setBatchNo(order.getBatchNo());
    sku.updatePurchaseInfo(itemSkuRepo, purchaseParam);

    StockChangeBatchParam changeParam = new StockChangeBatchParam();
    changeParam.setBizType(BizType.PURCHASE_IN.getValue())
        .setBizNo(order.batchNo)
        .setAdminId(1L)
        .setParams(Arrays.asList(
            new StockChangeSkuParam().setSkuId(order.getSkuId()).setIdempotentNo(order.getBatchNo()).setQuantity(order.getQuantity())
        ));
    ItemSku.changeStock(itemSkuRepo, billRepo, changeParam);

    return order;
  }


  public static Boolean updateSales(PurchaseOrderRepo repo, Long skuId, Long quantity) {
    PurchaseOrder purchaseOrder = repo.findLatestBySkuId(skuId);
    if(purchaseOrder==null){
      return Boolean.FALSE;
    }
    purchaseOrder.setSellCount(purchaseOrder.getSellCount() + quantity);
    repo.update(purchaseOrder);
    return Boolean.TRUE;
  }
}
