package top.zhacker.sample.retail.online.item.domain.item.sku;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import top.zhacker.boot.event.publish.DomainEventPublisher;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemSku;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockBill;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockBillRepo;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeBatchParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.bill.StockChangeSkuParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.event.StockChangeEvent;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdatePurchaseParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateSellParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.purchase.BatchNoBuilder;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;

import static java.util.stream.Collectors.toMap;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午5:47
 */
@Data
//@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class ItemSku {

  @ApiModelProperty("规格ID")
  private Long id;

  @ApiModelProperty("商品ID")
  private Long itemId;

  @ApiModelProperty("规格定义列表")
  private List<SkuSpecKeyValue> specs;

  @ApiModelProperty("成本价")
  private Long costPrice;

  @ApiModelProperty("结算价")
  private Long settlePrice;

  @ApiModelProperty("原价")
  private Long originPrice;

  @ApiModelProperty("销售价")
  private Long sellPrice;

  @ApiModelProperty("销售价划分规则")
  private SellPriceRule sellPriceRule;

  @ApiModelProperty("返回的点金石")
  private Long diamond;

  @ApiModelProperty("实时库存")
  private Long stock;

  @ApiModelProperty("规格状态")
  private Integer status;

  /**
   * @see BatchNoBuilder#build(Long)
   */
  @ApiModelProperty("当前批次号, 订单中要记录下在结算时使用")
  private String batchNo;

  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

  @Transient
  private Item item;

  public static ItemSku from(ItemSkuCreateParam param){
    return new ItemSku()
        .setSpecs(param.getSpecs())
        .setOriginPrice(0L)
        .setSellPrice(0L)
        .setCostPrice(0L)
        .setSettlePrice(0L)
        .setDiamond(0L)
        .setStock(0L)
        .setStatus(ItemSkuStatus.INIT.getValue())
        .setBatchNo("");
  }


  public ExhibitionItemSku toExhibitionSku(Long exhibitionId){
    return new ExhibitionItemSku()
        .setSkuId(id)
        .setItemId(itemId)
        .setDiamond(diamond)
        .setSellPrice(sellPrice)
        .setSellPriceRule(sellPriceRule)
        .setExhibitionId(exhibitionId);
  }

  public ItemSku merge(ExhibitionItemSku sku){
    if(sku==null){
      return this;
    }
    this.setSellPrice(sku.getSellPrice());
    this.setSellPriceRule(sku.getSellPriceRule());
    this.setDiamond(sku.getDiamond());
    return this;
  }

  public ItemSku save(ItemSkuRepo repo, Long itemId){
    this.itemId = itemId;
    repo.create(this);
    return this;
  }

  public static void batchUpdate(ItemSkuRepo repo, Long itemId, List<ItemSkuUpdateParam> params){

    //更新
    List<ItemSku> origin = repo.findByItemId(itemId);
    Map<Long,ItemSkuUpdateParam> paramMap =  params.stream().filter(param-> param.getId()!=0).collect(toMap(ItemSkuUpdateParam::getId, Function.identity()));
    origin.forEach(sku-> {
      Optional.ofNullable(paramMap.get(sku.getId())).ifPresent(param-> sku.merge(param));
    });
    origin.forEach(repo::update);

    //新增
    params.stream().filter(x->x.getId()==0)
        .map(x-> new ItemSkuCreateParam().setSpecs(x.getSpecs()))
        .map(ItemSku::from)
        .forEach(sku-> sku.save(repo, itemId));

  }

  public ItemSku merge(ItemSkuUpdateParam param){
    return this.setSpecs(param.getSpecs());
  }


  public ItemSku updateSellInfo(ItemSkuRepo repo, ItemSkuUpdateSellParam param) {
    if(Objects.equals(param.getSellPriceRule().getType(), SellType.SINGLE.getValue())){
      SellPriceRule rule = param.getSellPriceRule();
      if(rule.getCash()==null || rule.getCash()<=0){
        throw new BusinessException("单独支付时，现金不可为空");
      }
      if(rule.getDiamond()==null || rule.getDiamond()<=0){
        throw new BusinessException("单独支付时，点石金不可为空");
      }
      if(rule.getRemainder()==null || rule.getRemainder()<=0){
        throw new BusinessException("单独支付时，余额不可为空");
      }

    }

    this.setSellPrice(param.getSellPrice());
    if(param.getOriginPrice()==null) {
      this.setOriginPrice(param.getSellPrice()); //更新原价为销售价
    }else{
      this.setOriginPrice(param.getOriginPrice());
    }
    this.setSellPriceRule(param.getSellPriceRule());
    this.setDiamond(param.getDiamond());
    repo.update(this);
    return this;
  }

  public ItemSku updatePurchaseInfo(ItemSkuRepo repo, ItemSkuUpdatePurchaseParam param){
    this.setSettlePrice(param.getSettlePrice());
    this.setCostPrice(param.getCostPrice());
    this.setStock(param.getStock());
    this.setBatchNo(param.getBatchNo());
    repo.update(this);
    return this;
  }

  public ItemSku updateStatus(ItemSkuRepo repo, Integer toStatus){

    if(toStatus == ItemSkuStatus.ON_SHELF.getValue()){
      if(stock>0 && sellPriceRule!=null){
        this.setStatus(toStatus);
        repo.update(this);
      }else{
        throw new BusinessException("商品上架异常");
      }
    }else if(toStatus == ItemSkuStatus.OFF_SHELF.getValue()){
//      this.setStock(0L);
      this.setStatus(toStatus);
      repo.update(this);
    }
    return this;
  }

  public static Optional<ItemSku> findById(ItemSkuRepo repo, Long id){
    return Optional.ofNullable(repo.findById(id));
  }

  public static List<ItemSku> findByIds(ItemSkuRepo repo, List<Long> ids){
    return repo.findByIds(ids);
  }

  public static ItemSku load(ItemSkuRepo repo, Long id){
    return findById(repo,id).orElseThrow(()->new BusinessException("SKU不存在"));
  }


  public static boolean changeStock(ItemSkuRepo skuRepo, StockBillRepo billRepo, StockChangeBatchParam param){
    List<Long> skuIds = param.getParams().stream().map(StockChangeSkuParam::getSkuId).collect(Collectors.toList());
    val itemSkuMap = findByIds(skuRepo, skuIds).stream().collect(toMap(ItemSku::getId, Function.identity()));

    skuIds.stream()
        .filter(skuId-> ! itemSkuMap.containsKey(skuId)).findAny()
        .ifPresent((skuId)-> new BusinessException("SKU不存在"));

    if(Objects.equals(param.getBizType(), BizType.SELL_OUT.getValue())) {
      param.getParams().forEach(sku -> {
        ItemSku origin = itemSkuMap.get(sku.getSkuId());
        if(origin.getStock()+sku.getQuantity() < 0 ){
          throw new BusinessException("SKU库存不足");
        }
      });
    }

    param.getParams().forEach(skuParam->{
      ItemSku origin = itemSkuMap.get(skuParam.getSkuId());

      val bill = StockBill.from(origin.getStock(), param, skuParam);
      billRepo.create(bill);

      val toUpdate = new ItemSku().setId(skuParam.getSkuId())
          .setStock(origin.getStock()+skuParam.getQuantity())
          .setVersion(origin.getVersion());
      int result = skuRepo.update(toUpdate);
      if(result==0){
        throw new BusinessException("规格库存更新失败");
      }

      DomainEventPublisher.publish(new StockChangeEvent()
          .setBizType(bill.getBizType())
          .setSkuId(bill.getSkuId())
          .setQuantity(bill.getQuantity())
          .setItemId(origin.getItemId())
          .setBatchNo(origin.getBatchNo()));

    });

    return true;
  }

  public static Boolean clearStockWhenOffline(ItemSkuRepo repo, StockBillRepo billRepo, Long skuId, Long userId) {
    ItemSku sku = load(repo, skuId);
    if(! Objects.equals(sku.getStatus(), ItemSkuStatus.OFF_SHELF.getValue())){
      throw new BusinessException("规格不在下架状态，不能清空库存");
    }

    String bizNo = BatchNoBuilder.build(skuId);

    StockChangeBatchParam param = new StockChangeBatchParam()
        .setAdminId(userId)
        .setBizNo(bizNo)
        .setBizType(BizType.CHECK_OUT.getValue())
        .setParams(Arrays.asList(
            new StockChangeSkuParam().setIdempotentNo(bizNo).setQuantity(sku.getStock()*-1).setSkuId(skuId)
        ));

    return changeStock(repo, billRepo, param);
  }
}
