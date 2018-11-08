package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.brand.Brand;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandCreateParam;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandDeleteParam;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandQueryParam;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandUpdateParam;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * Time 2018/1/10 下午9:39
 */
@RestController
@Slf4j
@RequestMapping("/v1/brand")
@ParamLog
public class BrandApi {
  
  @Autowired
  private BrandService brandService;
  
  @ApiOperation(value="创建品牌", produces = "application/json")
  @PostMapping
  public Result<Long> create(@RequestBody BrandCreateParam param){
    return Result.ok(brandService.create(param).getId());
  }
  
  @ApiOperation(value = "更新品牌")
  @PutMapping
  public Result<Boolean> update(@RequestBody BrandUpdateParam param){
    brandService.update(param);
    return Result.ok(Boolean.TRUE);
  }
  
  @ApiOperation(value = "删除品牌")
  @DeleteMapping
  public Result<Boolean> delete(@RequestBody BrandDeleteParam param){
    return Result.ok(brandService.delete(param));
  }
  
  
  @ApiOperation(value = "品牌分页")
  @GetMapping("/page")
  public Result<Page<Brand>> page(BrandQueryParam param){
    return Result.ok(brandService.page(param));
  }
  
  @ApiOperation(value = "品牌分页-列表")
  @GetMapping("/list")
  public Result<List<Brand>> list(BrandQueryParam param){
    return Result.ok(brandService.list(param));
  }
  
  @ApiOperation(value = "品牌分页-总数")
  @GetMapping("/count")
  public Result<Long> count(BrandQueryParam param){
    return Result.ok(brandService.count(param));
  }
  
  
  
  @ApiOperation(value = "根据ID获取品牌")
  @GetMapping("/{id}")
  public Result<Brand> get(@PathVariable Long id){
    return Result.ok(brandService.get(id));
  }
}
