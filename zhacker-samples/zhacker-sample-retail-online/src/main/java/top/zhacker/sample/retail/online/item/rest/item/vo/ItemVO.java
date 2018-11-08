package top.zhacker.sample.retail.online.item.rest.item.vo;

import top.zhacker.sample.retail.online.item.domain.item.spec.SkuSpec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ItemVO implements Serializable {

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

    @Size(max = 10, message = "商品标签列表最多为10个")
    @ApiModelProperty("商品标签列表")
    private List<String> tags = new ArrayList<>();

    @Size(min = 1, max = 5, message = "商品主图列表数量为[1,5]")
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
    private ItemPriceVO price;

    @ApiModelProperty("分类")
    private CategoryVO category;

    @ApiModelProperty("品牌")
    private BrandVO brand;

    @ApiModelProperty("规格")
    private List<ItemSkuVO> skus;

    @ApiModelProperty("商品详情")
    private ItemDetailVO detail;

    @ApiModelProperty("微信分享的标题")
    private String weixinTitle;

    @ApiModelProperty("微信分享的描述")
    private String weixinDesc;

    @ApiModelProperty("微信分享的图标url")
    private String weixinIconUrl;

    @ApiModelProperty("微信打开的对应连接url")
    private String weixinLinkerUrl;
}
