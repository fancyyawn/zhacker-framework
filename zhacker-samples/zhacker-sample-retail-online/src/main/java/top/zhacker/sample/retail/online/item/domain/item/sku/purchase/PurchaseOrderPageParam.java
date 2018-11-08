package top.zhacker.sample.retail.online.item.domain.item.sku.purchase;

import top.zhacker.sample.retail.online.item.common.PageParam;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/2/10 上午8:59
 */
@Data
@Accessors(chain = true)
public class PurchaseOrderPageParam extends PageParam {
  private Long vendorId;
  private Long skuId;
  private String batchNo;
}
