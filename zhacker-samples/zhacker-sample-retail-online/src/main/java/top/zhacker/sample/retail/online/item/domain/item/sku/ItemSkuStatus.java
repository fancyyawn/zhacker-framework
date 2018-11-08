package top.zhacker.sample.retail.online.item.domain.item.sku;

import lombok.Getter;


/**
 * 0待审核,1上架,-1下架
 * Created by zhacker.
 * Time 2018/2/11 上午8:38
 */
public enum ItemSkuStatus {
  INIT(0, "待审核"),
  ON_SHELF(1, "审核通过并上架"),
  OFF_SHELF(-1, "下架")
  ;

  @Getter
  private final int value;

  @Getter
  private final String name;

  ItemSkuStatus(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
