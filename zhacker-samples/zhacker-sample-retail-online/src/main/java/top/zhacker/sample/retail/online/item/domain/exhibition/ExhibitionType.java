package top.zhacker.sample.retail.online.item.domain.exhibition;

import lombok.Getter;


/**
 * 1普通会场, 2活动, 3单品推荐
 * Created by zhacker.
 * Time 2018/1/27 下午4:07
 */
public enum ExhibitionType {
  NORMAL(1, "普通会场"),
  ACTIVITY(2, "活动"),
  RECOMMEND(3, "单品推荐")
  ;

  @Getter
  private final int value;
  @Getter
  private final String name;


  ExhibitionType(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
