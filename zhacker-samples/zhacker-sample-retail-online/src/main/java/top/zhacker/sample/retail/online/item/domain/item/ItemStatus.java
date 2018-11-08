package top.zhacker.sample.retail.online.item.domain.item;

import java.util.Arrays;

import lombok.Getter;
import top.zhacker.core.exception.BusinessException;


/**
 * 0待审核,1审核通过,-1审核失败,2上架,-2下架
 * Created by zhacker.
 * Time 2018/1/11 下午2:01
 */
public enum ItemStatus {
  INIT(0, "待审核"),
  AUDIT_SUCCESS(1, "审核通过"),
  AUDIT_FAIL(-1, "审核失败"),
  ON_SHELF(2, "上架"),
  OFF_SHELF(-2, "下架")
  ;

  @Getter
  private final int value;

  @Getter
  private final String name;

  ItemStatus(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public static ItemStatus of(Integer status){
    return Arrays.stream(ItemStatus.values())
        .filter(s-> s.getValue() == status).findAny()
        .orElseThrow(()->new BusinessException("商品状态不正确"));
  }

  public static boolean valid(Integer fromStatus, Integer toStatus){

    ItemStatus from = of(fromStatus);
    ItemStatus to = of(toStatus);

    if(to.getValue()>= from.getValue()){
      return true;
    }
    if(from==INIT && to == AUDIT_FAIL){
      return true;
    }
    if(from == ON_SHELF && to == OFF_SHELF){
      return true;
    }
    return false;
  }
}
