
package top.zhacker.sample.retail.online.item.domain.item.sku.bill;

import top.zhacker.sample.retail.online.item.domain.item.sku.BizType;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSku;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@Setter(AccessLevel.PROTECTED)
public class StockBill implements Serializable {

  private static final long serialVersionUID = -992181938155310686L;
  
  @ApiModelProperty("流水单据自增ID")
  private Long id;
  
  @ApiModelProperty("ItemSku的id")
  private Long skuId;
  
  @ApiModelProperty("幂等号码")
  private String idempotentNo;
  
  @ApiModelProperty("业务单据单号")
  private String bizNo;
  
  /**
   * @see BizType
   */
  @ApiModelProperty("单据类型")
  private Integer bizType;
  
  @ApiModelProperty("数量 可正可负")
  private Long quantity;

  @ApiModelProperty("修改前的值")
  private Long beforeStock;
  
  @ApiModelProperty("修改后的值")
  private Long afterStock;
  
  @ApiModelProperty("操作者id")
  private Long adminId;
  
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  
  @Transient
  private ItemSku sku;
  
   public static StockBill from(Long originStock, StockChangeBatchParam stockChangeBatchParam, StockChangeSkuParam param){
     return new StockBill().setSkuId(param.getSkuId())
         .setAdminId(stockChangeBatchParam.getAdminId())
         .setQuantity(param.getQuantity())
         .setIdempotentNo(param.getIdempotentNo())
         .setBeforeStock(originStock)
         .setAfterStock(param.getQuantity()+originStock)
         .setBizNo(stockChangeBatchParam.getBizNo())
         .setBizType(stockChangeBatchParam.getBizType());
   }

}
