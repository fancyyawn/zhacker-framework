package top.zhacker.sample.retail.online.item.domain.mybatis;


import org.apache.ibatis.type.MappedTypes;

import java.util.List;

import top.zhacker.sample.retail.online.item.common.ListJsonTypeHandler;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpecKeyValue;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午7:15
 */
@MappedTypes({List.class})
public class SkuSpecKeyValueListTypeHandler extends ListJsonTypeHandler<SkuSpecKeyValue> {
  
}
