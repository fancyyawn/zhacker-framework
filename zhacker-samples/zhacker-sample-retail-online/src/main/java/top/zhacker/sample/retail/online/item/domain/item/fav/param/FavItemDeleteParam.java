package top.zhacker.sample.retail.online.item.domain.item.fav.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/3/1 下午8:10
 */
@Data
@Accessors(chain = true)
public class FavItemDeleteParam {
  @NotNull
  private Long userId;
  @NotNull
  private Long itemId;
}
