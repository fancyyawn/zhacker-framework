package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.item.search.ItemSearch;
import top.zhacker.sample.retail.online.item.domain.item.search.ItemSearchPageParam;
import top.zhacker.sample.retail.online.item.application.ItemSearchService;
import top.zhacker.core.response.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhanghecheng on 2018/3/14.
 */
@RestController
@Slf4j
@RequestMapping("/v1/item/keyword")
@ParamLog
public class ItemSearchApi {

  @Autowired
  private ItemSearchService searchService;

  @GetMapping
  public Result<Page<ItemSearch>> page(ItemSearchPageParam param){
    param.setField("title");
    return Result.ok(searchService.page(param));
  }
}
