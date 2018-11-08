//package top.zhacker.sample.retail.online.item.rest.item.api;
//
//import top.zhacker.sample.retail.online.item.common.Page;
//import top.zhacker.sample.retail.online.item.domain.item.fav.FavItemCreateParam;
//import top.zhacker.sample.retail.online.item.domain.item.fav.FavItemDeleteParam;
//import top.zhacker.sample.retail.online.item.domain.item.fav.FavItemPageParam;
//import top.zhacker.sample.retail.online.item.service.FavItemService;
//import top.zhacker.sample.retail.online.item.util.BeanUtil;
//import top.zhacker.sample.retail.online.item.rest.item.vo.FavItemVO;
//import top.zhacker.core.response.Result;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.extern.slf4j.Slf4j;
//
//
///**
// * Created by zhacker.
// * Time 2018/3/1 下午8:34
// */
//@RestController
//@Slf4j
//@RequestMapping("/v1/fav-item")
//public class FavItemApi{
//
//  @Autowired
//  private FavItemService favItemService;
//
//  @PostMapping
//  public Result<Long> create(@RequestBody FavItemCreateParam param){
////     param.setUserId(getUserId());
//     return Result.ok(favItemService.create(param));
//  }
//
//  @GetMapping("/{itemId}")
//  public Result<Boolean> inFav(@PathVariable("itemId") Long itemId){
//    return Result.ok(favItemService.inFav(getUserId(), itemId));
//  }
//
//  @DeleteMapping("/{itemId}")
//  public Result<Boolean> delete(@PathVariable("itemId") Long itemId){
//    return Result.ok(favItemService.delete(new FavItemDeleteParam().setUserId(getUserId()).setItemId(itemId)));
//  }
//
//  @GetMapping
//  public Result<Page<FavItemVO>> page(FavItemPageParam param){
////    param.setUserId(getUserId());
//    return Result.ok(BeanUtil.deepCopyPage(favItemService.page(param), FavItemVO.class));
//  }
//}
