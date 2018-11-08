package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.application.exhibition.param.*;
import top.zhacker.sample.retail.online.item.domain.exhibition.*;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItem;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.ExhibitionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午9:33
 */
@RestController
@RequestMapping("/v1/exhibition")
@ParamLog
public class ExhibitionApi {
  
  @Autowired
  private ExhibitionService exhibitionService;
  
  @GetMapping("/list")
  public Result<List<Exhibition>> list(ExhibitionQueryParam param){
    return Result.ok(exhibitionService.page(param));
  }
  
  @GetMapping("/count")
  public Result<Long> count(ExhibitionQueryParam param){
    return Result.ok(exhibitionService.count(param));
  }
  
  @GetMapping("/{id}")
  public Result<Exhibition> findById(@PathVariable("id") Long id){
    return Result.ok(exhibitionService.findById(id));
  }
  
  @PostMapping
  public Result<Long> create(@RequestBody ExhibitionCreateParam param){
    return Result.ok(exhibitionService.create(param).getId());
  }
  
  @PutMapping
  public Result<Boolean> update(@RequestBody ExhibitionUpdateParam param){
    return Result.ok(exhibitionService.update(param));
  }
  
  @PutMapping("/{id}/status/{status}")
  public Result<Boolean> updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status){
    return Result.ok(exhibitionService.updateStatus(new ExhibitionUpdateStatusParam().setId(id).setStatus(status)));
  }

  @PostMapping("/{id}/copy")
  public Result<List<ExhibitionItem>> copy(@PathVariable("id") Long id, @RequestBody ExhibitionCopyParam param){
    param.setExhibitionId(id);
    return Result.ok(exhibitionService.copy(param));
  }
  
}
