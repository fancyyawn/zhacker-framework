package top.zhacker.sample.retail.online.item.domain.item.sku.event;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.core.model.BaseDomainEvent;


/**
 * Created by zhacker.
 * Time 2018/2/13 下午5:26
 */
@Data
@Accessors(chain = true)
public class StockChangeEvent extends BaseDomainEvent {
  private Integer bizType;
  private Long itemId;
  private Long skuId;
  private Long quantity;
  private String batchNo;
}
