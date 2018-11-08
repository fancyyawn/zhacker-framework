package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class CategoryVO implements Serializable {

    @ApiModelProperty("分类ID")
    private Long id;
    @ApiModelProperty("父分类ID，根分类时为0")
    private Long parentId;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("分类图片，为合法图片URL")
    private String banner;
    @ApiModelProperty("是否显示分类图片")
    private Boolean showBanner;
    @ApiModelProperty("是否为叶子分类")
    private Boolean leaf;

    @ApiModelProperty("微信分享的标题")
    private String weixinTitle;

    @ApiModelProperty("微信分享的描述")
    private String weixinDesc;

    @ApiModelProperty("微信分享的图标url")
    private String weixinIconUrl;

    @ApiModelProperty("微信打开的对应连接url")
    private String weixinLinkerUrl;
}
