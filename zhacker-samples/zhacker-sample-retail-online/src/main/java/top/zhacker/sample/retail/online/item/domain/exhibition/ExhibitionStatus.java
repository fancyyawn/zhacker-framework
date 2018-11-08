package top.zhacker.sample.retail.online.item.domain.exhibition;

import lombok.Getter;


/**
 * 0未激活, 1正常, -1已过期
 * Created by zhacker.
 * Time 2018/1/27 下午4:15
 */
public enum  ExhibitionStatus {
  INACTIVE(0, "未激活"),
  ACTIVE(1, "正常"),
  EXPIRED(-1, "已过期"),
  OFFLINE(-2, "下架")
  ;

  @Getter
  private final int value;
  @Getter
  private final String name;

  ExhibitionStatus(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
