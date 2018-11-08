package top.zhacker.sample.retail.online.item.rest.item.api;

import top.zhacker.boot.aop.log.ParamLog;
import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemPageParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateStatusParam;
import top.zhacker.sample.retail.online.item.domain.item.snapshot.ItemSnapshot;
import top.zhacker.core.response.Result;
import top.zhacker.sample.retail.online.item.application.ItemService;

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
 * Time 2018/1/9 下午9:52
 */
@RestController
@Slf4j
@RequestMapping("/v1/item")
@ParamLog
public class ItemApi {

  @Autowired
  private ItemService itemService;

  @ApiOperation("商品创建")
  @PostMapping
  public Result<Long> create(@RequestBody ItemCreateParam param){
//      param.setVendorId(UserContext.getLoginUserId());
    return Result.ok(itemService.create(param).getId());
  }

  @ApiOperation("更新商品信息")
  @PutMapping("/{id}")
  public Result<Boolean> update(@PathVariable Long id, @RequestBody ItemUpdateParam param){
    param.setId(id);
    return Result.ok(itemService.update(param));
  }

  @ApiOperation("更新商品状态")
  @PutMapping("/{id}/status")
  public Result<Boolean> updateStatus(@PathVariable Long id, @RequestBody ItemUpdateStatusParam param){
    param.setItemId(id);
    return Result.ok(itemService.updateStatus(param));
  }

  @ApiOperation("获取商品完整信息")
  @GetMapping("/{id}")
  public Result<Item> loadWithSkuAndDetail(@PathVariable Long id){
    return Result.ok(itemService.loadWithSkuAndDetail(id));
  }

  @ApiOperation("获取商品分页，不包含详情，用于首页等地方")
  @GetMapping("/page")
  public Result<Page<Item>> page(ItemPageParam param){
    return Result.ok(new Page<>(itemService.count(param), itemService.page(param)));
  }

  @ApiOperation("获取商品分页-数据，不包含详情，用于首页等地方")
  @GetMapping("/list")
  public Result<List<Item>> list(ItemPageParam param){
    return Result.ok(itemService.page(param));
  }

  @ApiOperation("获取商品分页-总数，不包含详情，用于首页等地方")
  @GetMapping("/count")
  public Result<Long> count(ItemPageParam param){
    return Result.ok(itemService.count(param));
  }

  @PostMapping("/{id}/snapshot")
  public Result<Long> createSnapshot(@PathVariable("id") Long id){
    return Result.ok(itemService.createSnapshot(id));
  }

  @GetMapping("/snapshot/{id}")
  public Result<ItemSnapshot> findSnapshot(@PathVariable("id") Long id){
    return Result.ok(itemService.findSnapshot(id));
  }

  @ApiOperation("复制商品信息")
  @PostMapping("/{id}/copy")
  public Result<Long> copy(@PathVariable Long id){
    return Result.ok(itemService.copy(id));
  }

}
