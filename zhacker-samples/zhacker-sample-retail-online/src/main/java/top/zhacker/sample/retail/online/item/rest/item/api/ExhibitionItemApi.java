package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.domain.exhibition.Exhibition;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.*;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.*;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.ExhibitionService;
import top.zhacker.sample.retail.online.item.application.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/2/13 下午2:04
 */
@RestController
@RequestMapping("/v1/exhibition")
@ParamLog
public class ExhibitionItemApi {
  
  @Autowired
  private ExhibitionService exhibitionService;
  
  @Autowired
  private ItemService itemService;
  
  @GetMapping("/{id}/item/{itemId}")
  public Result<Item> loadItem(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId){
    return Result.ok(itemService.loadExhibitionItem(itemId,id));
  }
  
  
  @PostMapping("/item/add")
  public Result<Boolean> addItems(@RequestBody ExhibitionItemAddParam param){
    return Result.ok(exhibitionService.addItems(param));
  }
  
  @PostMapping("/item/remove")
  public Result<Boolean> removeItems(@RequestBody ExhibitionItemDeleteParam param){
    return Result.ok(exhibitionService.removeItems(param));
  }
  
  
  @PostMapping("/item/update")
  public Result<Boolean> updateItem(@RequestBody ExhibitionItemUpdateParam param){
    return Result.ok(exhibitionService.updateItem(param));
  }

  @GetMapping("/{id}/item/{itemId}/valid")
  public Result<List<ExhibitionItem>> valid(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId){
    Exhibition exhibition = exhibitionService.findById(id);

    ExhibitionItemQueryParam queryParam = new ExhibitionItemQueryParam()
        .setType(exhibition.getType())
        .setItemId(itemId)
        .setStartAt(exhibition.getStartAt())
        .setEndAt(exhibition.getEndAt());

    return Result.ok(exhibitionService.query(queryParam));
  }
  
}
