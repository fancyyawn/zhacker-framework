package top.zhacker.sample.retail.online.item.domain.item.param;

import top.zhacker.sample.retail.online.item.domain.item.detail.param.ItemDetailUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.param.ItemSkuUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午9:12
 */
@Data
@Accessors(chain = true)
public class ItemUpdateParam {
  
  @NotNull(message = "商品ID不能为空")
  @ApiModelProperty("商品ID")
  private Long id;
  
  @ApiModelProperty("商品分类ID")
  private Long categoryId;
  
  @ApiModelProperty("商品品牌ID")
  private Long brandId;
  
  @ApiModelProperty("商品标签列表")
  private List<String> tags = new ArrayList<>();
  
  @ApiModelProperty("商品主图列表，最多为5张")
  private List<String> photos = new ArrayList<>();
  
  @ApiModelProperty("商品标题")
  private String title;
  
  @ApiModelProperty("商品编码，具有唯一性")
  private String itemNo;
  
  @ApiModelProperty("推荐数：0-100，默认为0")
  private Integer recommendScore;
  
  @ApiModelProperty("商品规格定义列表")
  private List<SkuSpec> specs = new ArrayList<>();
  
  private List<ItemSkuUpdateParam> skus = new ArrayList<>();
  
  private ItemDetailUpdateParam detail = new ItemDetailUpdateParam();
  
}
