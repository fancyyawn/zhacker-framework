package top.zhacker.sample.retail.online.item.domain.item.sku.bill;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/20 上午11:39
 */
@Data
@Accessors(chain = true)
public class StockChangeBatchParam {
  
  @ApiModelProperty("业务单据单号")
  private String bizNo;
  
  @ApiModelProperty("单据类型")
  private Integer bizType;
  
  @ApiModelProperty("操作者id")
  private Long adminId;
  
  @ApiModelProperty("Sku库存变更")
  private List<StockChangeSkuParam> params = new ArrayList<>();
  
}
