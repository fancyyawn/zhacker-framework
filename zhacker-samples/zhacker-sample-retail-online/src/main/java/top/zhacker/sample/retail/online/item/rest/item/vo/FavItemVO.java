package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class FavItemVO implements Serializable {

  private Long id;
  private Long userId;
  private Long itemId;
  private ItemVO item;
}
