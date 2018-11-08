package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午9:46
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionWithItemsVO {
  
  private ExhibitionVO exhibition;
  
  private List<ItemVO> items = new ArrayList<>();
}
