package top.zhacker.sample.retail.online.item.domain.mybatis;

import org.apache.ibatis.type.MappedTypes;

import java.util.List;

import top.zhacker.sample.retail.online.item.common.ListJsonTypeHandler;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemSku;


/**
 * Created by zhacker.
 * Time 2018/2/10 下午3:52
 */
@MappedTypes({List.class})
public class ExhibitionItemSkuListTypeHandler extends ListJsonTypeHandler<ExhibitionItemSku> {
  
}
