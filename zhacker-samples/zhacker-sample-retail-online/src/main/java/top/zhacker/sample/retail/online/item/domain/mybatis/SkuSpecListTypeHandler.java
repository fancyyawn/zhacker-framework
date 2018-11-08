package top.zhacker.sample.retail.online.item.domain.mybatis;


import org.apache.ibatis.type.MappedTypes;

import java.util.List;

import top.zhacker.sample.retail.online.item.common.ListJsonTypeHandler;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;


/**
 * Created by zhacker.
 * Time 2018/1/12 下午10:27
 */
@MappedTypes({List.class})
public class SkuSpecListTypeHandler extends ListJsonTypeHandler<SkuSpec> {
  
}
