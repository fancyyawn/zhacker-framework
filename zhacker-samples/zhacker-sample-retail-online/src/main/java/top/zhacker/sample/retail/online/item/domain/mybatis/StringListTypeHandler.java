package top.zhacker.sample.retail.online.item.domain.mybatis;

import org.apache.ibatis.type.MappedTypes;

import java.util.List;

import top.zhacker.sample.retail.online.item.common.ListJsonTypeHandler;


/**
 * Created by zhacker.
 * Time 2018/1/12 下午10:26
 */
@MappedTypes({List.class})
public class StringListTypeHandler extends ListJsonTypeHandler<String> {
  
}
