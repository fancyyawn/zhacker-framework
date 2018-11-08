package top.zhacker.sample.retail.online.item.domain.item.param;


import com.google.common.base.Strings;

import top.zhacker.sample.retail.online.item.domain.brand.Brand;
import top.zhacker.sample.retail.online.item.domain.brand.BrandRepo;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItem;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemRepo;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午8:31
 */
@Data
@Accessors(chain = true)
public class ItemPageParam {
  
  @ApiModelProperty("活动ID")
  private Long exhibitionId;
  
  @ApiModelProperty("商品ID")
  private List<Long> itemIds;
  
  @ApiModelProperty("品牌ID")
  private List<Long> brandIds;
  
  @ApiModelProperty("品牌名称模糊搜索")
  private String brandPart;
  
  @ApiModelProperty("商品标题模糊搜索")
  private String titlePart;
  
  @ApiModelProperty("商品编码")
  private String itemNo;
  
  @ApiModelProperty("供应商")
  private Long vendorId;
  
  @ApiModelProperty("分类ID")
  private Long categoryId;
  
  @ApiModelProperty("店铺ID")
  private Long shopId;
  
  @ApiModelProperty("商品状态")
  private Integer status;
  
  @ApiModelProperty("分页号，从1开始")
  private Integer pageNo = 1;
  
  @ApiModelProperty("分页大小")
  private Integer pageSize = 20;
  
  @ApiModelProperty("推荐分数开始值")
  private Integer recommendScoreStart;
  
  @ApiModelProperty("推荐分数截止值")
  private Integer recommendScoreEnd;
  
  private Boolean sortedByScore;
  
  @ApiModelProperty("是否加载关联的分类和品牌信息")
  private Boolean loadCategoryAndBrand = false;

  @ApiModelProperty("是否过滤不合理的sku")
  private Boolean filterInvalidSkus = false;
  
  public Integer getLimit(){
    return pageSize;
  }
  
  public Integer getOffset(){
    return (pageNo-1)*pageSize;
  }
  
  public ItemPageParam loadBrands(BrandRepo brandRepo){
    
    if(! Strings.isNullOrEmpty(brandPart)) {
      
      brandIds = brandRepo.findByNamePart(brandPart).stream()
          .map(Brand::getId)
          .collect(Collectors.toList());
    }
    
    return this;
  }
  
  public ItemPageParam loadItemIdsForExhibit(ExhibitionItemRepo repo){
    if(exhibitionId != null){
       itemIds = repo.findByExhibitionId(exhibitionId).stream().map(ExhibitionItem::getItemId).collect(Collectors.toList());
    }
    return this;
  }
}
