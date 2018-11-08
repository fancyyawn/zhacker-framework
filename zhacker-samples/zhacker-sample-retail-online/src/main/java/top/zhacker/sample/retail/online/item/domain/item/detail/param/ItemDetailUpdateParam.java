package top.zhacker.sample.retail.online.item.domain.item.detail.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午9:12
 */
@Data
@Accessors(chain = true)
public class ItemDetailUpdateParam {
  
  private Long itemId;
  
  private String desc;
  
  private List<String> photos = new ArrayList<>();
}
