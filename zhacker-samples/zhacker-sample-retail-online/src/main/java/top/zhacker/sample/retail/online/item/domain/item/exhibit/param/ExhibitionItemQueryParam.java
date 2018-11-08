package top.zhacker.sample.retail.online.item.domain.item.exhibit.param;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ExhibitionItemQueryParam {
  private Integer type;
  private Long itemId;
  private Date startAt;
  private Date endAt;
  private List<Integer> statuses;
}
