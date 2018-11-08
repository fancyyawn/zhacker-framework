package top.zhacker.sample.retail.online.item.rest.item.vo;

import top.zhacker.sample.retail.online.item.domain.price.PriceUtil;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class CartItemVO implements Serializable {

  private Long id;
  private Long buyerId;
  private Long skuId;
  private Long itemId;
  private Long exhibitionId;
  private Long quantity;
  private Long snapshotPrice;
  private ItemVO item;
  private ItemSkuVO sku;
  private ExhibitionVO exhibition;

  private String getSnapshotPriceYuan(){
    return PriceUtil.formatYuan(snapshotPrice);
  }
}
