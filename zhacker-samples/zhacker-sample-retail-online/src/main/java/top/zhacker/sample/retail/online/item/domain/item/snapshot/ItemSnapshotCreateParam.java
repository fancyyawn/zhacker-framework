package top.zhacker.sample.retail.online.item.domain.item.snapshot;

import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.application.ItemService;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午5:07
 */
@Data
@Accessors(chain = true)
public class ItemSnapshotCreateParam {
  
  private Long itemId;
  
  public ItemSnapshot build(ItemService itemService){
    Item item = itemService.loadWithSkuAndDetail(itemId);
    return new ItemSnapshot().setItemId(itemId).setSnapshot(item.toVO()).computeItemMd5();
  }
}
