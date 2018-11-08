package top.zhacker.sample.retail.online.item.domain.item.checker;

import top.zhacker.sample.retail.online.item.common.CheckerRegistry;
import top.zhacker.sample.retail.online.item.domain.item.Item;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


/**
 * Created by zhacker.
 * Time 2018/2/14 上午10:20
 */
@Component
@Validated
public class ItemCheckers extends CheckerRegistry<Item> {
  
}
