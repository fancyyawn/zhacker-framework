package top.zhacker.sample.retail.online.item.domain.item.fav.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:09
 */
@Data
@Accessors(chain = true)
public class FavItemCreateParam {
  @NotNull
  private Long userId;
  @NotNull
  private Long itemId;
}
