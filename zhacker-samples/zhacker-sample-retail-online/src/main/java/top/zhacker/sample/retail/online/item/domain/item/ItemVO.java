package top.zhacker.sample.retail.online.item.domain.item;

import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetailVO;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuVO;
import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/28 下午5:58
 */
@Data
@Accessors(chain = true)
public class ItemVO {
  
  @ApiModelProperty("商品ID")
  private Long id;
  @ApiModelProperty("商品关联的店铺ID")
  private Long shopId;
  @ApiModelProperty("商品关联的供应商ID")
  private Long vendorId = 0L;
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
  @ApiModelProperty("商品规格定义列表")
  private List<SkuSpec> specs = new ArrayList<>();
  @ApiModelProperty("商品状态")
  private Integer status;
  @ApiModelProperty("销量")
  private Long sales;
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  @ApiModelProperty("商品价格")
  private ItemPrice price;
  
  private List<ItemSkuVO> skus = new ArrayList<>();
  
  private ItemDetailVO detail;
}
