package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.domain.category.Category;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryCreateParam;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryUpdateParam;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午10:00
 */
@RestController
@Slf4j
@RequestMapping("/v1/category")
@ParamLog
public class CategoryApi {
  
  @Autowired
  private CategoryService categoryService;
  
  @ApiOperation("获取分类")
  @GetMapping("/{id}")
  public Result<Category> findById(@PathVariable("id") Long id){
    return Result.ok(categoryService.findById(id));
  }
  
  @ApiOperation("获取所有分类")
  @GetMapping
  public Result<List<Category>> findAll(){
    return Result.ok(categoryService.findAll());
  }
  
  @ApiOperation("创建分类")
  @PostMapping
  public Result<Long> create(@RequestBody CategoryCreateParam param){
    return Result.ok(categoryService.create(param).getId());
  }
  
  @ApiOperation("更新分类")
  @PutMapping
  public Result<Boolean> update(@RequestBody CategoryUpdateParam param){
    categoryService.update(param);
    return Result.ok(Boolean.TRUE);
  }
}
