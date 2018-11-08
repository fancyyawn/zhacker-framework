package top.zhacker.sample.retail.online.item.domain.item.search;

import top.zhacker.sample.retail.online.item.common.PageParam;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/14.
 */
@Data
@Accessors(chain = true)
public class ItemSearchPageParam extends PageParam {

  private String field;

}
