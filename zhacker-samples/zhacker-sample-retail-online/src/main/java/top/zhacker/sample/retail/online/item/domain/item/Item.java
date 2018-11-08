package top.zhacker.sample.retail.online.item.domain.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.Operation;
import top.zhacker.sample.retail.online.item.common.Spec;
import top.zhacker.sample.retail.online.item.domain.brand.Brand;
import top.zhacker.sample.retail.online.item.domain.brand.BrandRepo;
import top.zhacker.sample.retail.online.item.domain.category.Category;
import top.zhacker.sample.retail.online.item.domain.category.CategoryRepo;
import top.zhacker.sample.retail.online.item.domain.item.checker.ItemBrandChecker;
import top.zhacker.sample.retail.online.item.domain.item.checker.ItemCategoryChecker;
import top.zhacker.sample.retail.online.item.domain.item.checker.ItemCheckers;
import top.zhacker.sample.retail.online.item.domain.item.checker.ItemPhotosChecker;
import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetail;
import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetailRepo;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItem;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemSku;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemPageParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateStatusParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSku;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;
import top.zhacker.sample.retail.online.item.util.BeanUtil;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午7:39
 */
@Data
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class Item {

  @ApiModelProperty("商品ID")
  private Long id;

  @ApiModelProperty("商品关联的店铺ID")
  private Long shopId;

  @ApiModelProperty("商品关联的供应商ID")
  private Long vendorId = 0L;

  @Spec(ItemCategoryChecker.class)
  @ApiModelProperty("商品分类ID")
  private Long categoryId;

  @Spec(ItemBrandChecker.class)
  @ApiModelProperty("商品品牌ID")
  private Long brandId;

  @Size(max = 10, message = "商品标签列表最多为10个")
  @ApiModelProperty("商品标签列表")
  private List<String> tags = new ArrayList<>();

  @Size(min = 1, max = 5, message = "商品主图列表数量为[1,5]")
  @Spec(ItemPhotosChecker.class)
  @ApiModelProperty("商品主图列表，最多为5张")
  private List<String> photos = new ArrayList<>();

  @Size(min = 1, max = 200, message = "商品标题字符数为[1,200]")
  @ApiModelProperty("商品标题")
  private String title;

  @ApiModelProperty("商品编码，具有唯一性")
  private String itemNo;

  @Size(min = 1, max = 2, message = "商品规格定义列表数量为[1,2]")
  @ApiModelProperty("商品规格定义列表")
  private List<SkuSpec> specs = new ArrayList<>();

  @ApiModelProperty("商品状态")
  private Integer status;

  @ApiModelProperty("销量")
  private Long sales;

  @ApiModelProperty("商品价格")
  private ItemPrice price;

  @ApiModelProperty("推荐数：0-100，默认为0")
  @Min(value = 0, message = "最小分数为0")
  @Max(value = 100, message = "最大分数为100")
  private Integer recommendScore;

  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

  @Transient
  private Category category;

  @Transient
  private Brand brand;

  @Transient
  private List<ItemSku> skus = new ArrayList<>();

  @Transient
  private ItemDetail detail;

  public ItemVO toVO(){
    return BeanUtil.deepCopy(this, ItemVO.class);
  }

  protected Item(){}

  public Item merge(ExhibitionItem item){
    if(item==null){
      return this;
    }
    setPrice(item.getPrice());
    if(skus==null){
      return this;
    }
    val exhibitSkus = item.getSkus().stream().collect(Collectors.toMap(ExhibitionItemSku::getSkuId, Function.identity()));
    skus.forEach(sku->sku.merge(exhibitSkus.get(sku.getId())));
    return this;
  }

  public static Item create(ItemManager manager, ItemCreateParam param, ItemCheckers checkers){
    Item item = from(param);
    checkers.check(item, Operation.Create);
    return manager.save(item);
  }

  public static Item from(ItemCreateParam param){
    Item item = new Item();
    item.setShopId(param.getShopId())
        .setVendorId(param.getVendorId())
        .setCategoryId(param.getCategoryId())
        .setBrandId(param.getBrandId())
        .setTags(param.getTags())
        .setPhotos(param.getPhotos())
        .setTitle(param.getTitle())
        .setItemNo(param.getItemNo())
        .setSpecs(param.getSpecs())
        .setStatus(ItemStatus.INIT.getValue())
        .setSales(0L)
        .setRecommendScore(param.getRecommendScore())
        .setDetail(ItemDetail.from(param.getDetail(), ItemDetail.class))
        .setPrice(new ItemPrice().setOriginPrice(0L).setSellPrice(0L).setDiamond(0L))
        .setSkus(param.getSkus().stream().map(ItemSku::from).collect(Collectors.toList()));
    return item;
  }

  public static Item updateBasic(ItemRepo repo, ItemUpdateParam param, ItemCheckers checkers){

    checkers.check(new Item().merge(param), Operation.Update);

    Item item = Item.load(repo, param.getId());

    item.merge(param);

    repo.update(item);

    return item;
  }
  public Item merge(ItemUpdateParam param){
    this.setId(param.getId())
        .setCategoryId(param.getCategoryId())
        .setBrandId(param.getBrandId())
        .setTags(param.getTags())
        .setPhotos(param.getPhotos())
        .setTitle(param.getTitle())
        .setItemNo(param.getItemNo())
        .setSpecs(param.getSpecs())
        .setRecommendScore(param.getRecommendScore());
    return this;
  }

  public static Boolean updateStatus(ItemRepo repo, ItemUpdateStatusParam param){
    Item item = Item.load(repo, param.getItemId());
    if(! ItemStatus.valid(item.getStatus(), param.getStatus())){
      throw new BusinessException("商品状态转换不合理");
    }
    item.setStatus(param.getStatus());
    repo.update(item);

    return Boolean.TRUE;
  }

  public static Boolean updateSales(ItemRepo repo, Long itemId, Long sales){

    Item item = load(repo, itemId);
    item.setSales(item.getSales()+sales);
    repo.update(item);

    return Boolean.TRUE;
  }

  public Boolean updatePrice(ItemRepo repo, ItemPrice price){
    this.setPrice(price);
    repo.update(this);
    return Boolean.TRUE;
  }

  public static Item load(ItemRepo repo, Long id){
    return Optional.ofNullable(repo.findById(id)).orElseThrow(()->new BusinessException("商品不存在"));
  }

  public static Long count(ItemRepo repo, ItemPageParam param){
    return repo.count(param);
  }

  public static List<Item> page(ItemRepo repo, ItemPageParam param){
    return repo.page(param);
  }

  public Item loadBrand(BrandRepo repo){
    brand = repo.findById(brandId);
    return this;
  }

  public Item loadCategory(CategoryRepo repo){
    category = repo.findById(categoryId);
    return this;
  }

  public Item loadSkus(ItemSkuRepo repo){
    if(id!=null) {
      skus = repo.findByItemId(id);
    }
    return this;
  }

  public Item loadDetail(ItemDetailRepo repo){
    if(id!=null) {
      detail = repo.findByItemId(id);
    }
    return this;
  }

  public Optional<ItemSku> getSku(Long skuId){
      return skus.stream().filter(sku-> Objects.equals(skuId, sku.getId())).findFirst();
  }
}
