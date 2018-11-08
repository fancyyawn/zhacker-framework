package top.zhacker.sample.retail.online.item.domain.item.param;

import top.zhacker.sample.retail.online.item.domain.item.detail.param.ItemDetailCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午7:53
 */
@Data
@Accessors(chain = true)
public class ItemCreateParam {
  
  @NotNull(message = "商品关联的店铺不能为空")
  @ApiModelProperty("商品关联的店铺ID")
  private Long shopId;
  
  @NotNull(message = "商品关联的供应商不能为空")
  @ApiModelProperty("商品关联的供应商ID")
  private Long vendorId;
  
  @NotNull(message = "商品分类不能为空")
  @ApiModelProperty("商品分类ID")
  private Long categoryId;
  
  @NotNull(message = "商品品牌不能为空")
  @ApiModelProperty("商品品牌ID")
  private Long brandId;
  
  @NotNull(message = "商品标签列表不能为空")
  @ApiModelProperty("商品标签列表")
  private List<String> tags = new ArrayList<>();
  
  @NotNull(message = "商品主图列表不能为空")
  @ApiModelProperty("商品主图列表，最多为5张")
  private List<String> photos = new ArrayList<>();
  
  @NotNull(message = "商品标题不能为空")
  @ApiModelProperty("商品标题")
  private String title;
  
  @ApiModelProperty("商品编码，具有唯一性")
  private String itemNo;
  
  @NotNull(message = "商品规格定义列表不能为空")
  @ApiModelProperty("商品规格定义列表")
  private List<SkuSpec> specs = new ArrayList<>();
  
  @ApiModelProperty("详情")
  private ItemDetailCreateParam detail = new ItemDetailCreateParam();
  
  @ApiModelProperty("具体规格，要求和规格定义列表中的值相对应")
  private List<ItemSkuCreateParam> skus = new ArrayList<>();
  
  @ApiModelProperty("推荐数：0-100，默认为0")
  private Integer recommendScore = 0;

}
