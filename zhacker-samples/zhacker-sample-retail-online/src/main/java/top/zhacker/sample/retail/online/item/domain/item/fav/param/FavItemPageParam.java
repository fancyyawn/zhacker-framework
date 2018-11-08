package top.zhacker.sample.retail.online.item.domain.item.fav.param;

import top.zhacker.sample.retail.online.item.common.PageParam;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:15
 */
@Data
@Accessors(chain = true)
public class FavItemPageParam extends PageParam {
  
  private Long userId;
}
