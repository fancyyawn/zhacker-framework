package top.zhacker.sample.retail.online.item.domain.item.sku;

import lombok.Getter;


/**
 * 11销售出库，12盘点出库，21采购入库，22订单取消入库，23订单退货入库
 * Created by zhacker.
 * Time 2018/1/20 下午2:11
 */
public enum BizType {

  SELL_OUT(11, "销售出库"),
    CHECK_OUT(12, "盘点出库"),
  PURCHASE_IN(21, "采购入库"),
  ORDER_CANCEL_IN(22, "订单取消入库"),
  ORDER_RETURN_IN(23,"订单退货入库");

  @Getter
  private final int value;

  @Getter
  private final String name;

  BizType(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
