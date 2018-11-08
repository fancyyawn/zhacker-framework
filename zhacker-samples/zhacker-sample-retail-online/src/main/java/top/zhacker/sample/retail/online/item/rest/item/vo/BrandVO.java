package top.zhacker.sample.retail.online.item.rest.item.vo;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhanghecheng on 2018/3/10.
 */
@Data
@Accessors(chain = true)
public class BrandVO implements Serializable {

    @ApiModelProperty("品牌ID")
    private Long id;

    @Size(min=1, max=100, message = "品牌名称字符数限制为[1,100]")
    @ApiModelProperty("品牌名称")
    private String name;

    @Pattern(regexp = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]", message = "logo必须为合法的URL")
    @Size(min=1, max = 128, message = "品牌名称长度必须在1-128字符内")
    @ApiModelProperty("品牌logo，为标准的URL图片格式")
    private String logo;
}
