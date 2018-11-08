package top.zhacker.sample.retail.online.item.domain.item.sku;

import lombok.Getter;


/**
 * Created by zhacker.
 * Time 2018/2/9 下午10:18
 */
public enum SellType {

  SINGLE(0, "现金 / 余额 / 点金石"),
  DIAMOND(1, "现金+点金石"),
  DIAMOND_REMAINDER(6, "余额+点金石"),
  TREASURE(2, "现金+九洲宝"),
  REMAINDER(3, "现金+余额"),
  DIAMOND_TREASURE(4, "现金+点金石+九洲宝"),
  DIAMOND_TREASURE_REMAINDER(5, "现金+点金石+九洲宝+余额");


  @Getter
  private final int value;

  @Getter
  private final String name;

  SellType(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
