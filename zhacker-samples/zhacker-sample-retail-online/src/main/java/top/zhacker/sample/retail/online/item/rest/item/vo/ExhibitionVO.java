package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class ExhibitionVO implements Serializable {

    private Long id;
    @ApiModelProperty("会场名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("主图")
    private String mainPhoto;
    @ApiModelProperty("导航图，用于首页")
    private String navigatePhoto;
    @ApiModelProperty("开始时间")
    private Date startAt;
    @ApiModelProperty("截止时间")
    private Date endAt;
    @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
    private Integer type;
    @ApiModelProperty("展会的状态：0未激活，1正常，-1过期")
    private Integer status;

    @ApiModelProperty("微信分享的标题")
    private String weixinTitle;

    @ApiModelProperty("微信分享的描述")
    private String weixinDesc;

    @ApiModelProperty("微信分享的图标url")
    private String weixinIconUrl;

    @ApiModelProperty("微信打开的对应连接url")
    private String weixinLinkerUrl;
}
