package top.zhacker.sample.retail.online.item.domain.exhibition;

import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionCopyParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionCreateParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionQueryParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionUpdateParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionUpdateStatusParam;
import top.zhacker.sample.retail.online.item.util.BeanUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/1/24 下午9:24
 */
@Data
@Accessors(chain = true)
public class Exhibition implements Serializable {
  
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
  
  /**
   * @see ExhibitionType
   */
  @ApiModelProperty("类型：1会场，2活动, 3单品推荐")
  private Integer type;
  
  /**
   * @see ExhibitionStatus
   */
  @ApiModelProperty("展会的状态：0未激活，1正常，-1过期, -2下架")
  private Integer status;

  @ApiModelProperty("排序分值")
  private Long score;
  
  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  
  
  public static Exhibition create(ExhibitionRepo repo, ExhibitionCreateParam param){
    return from(param).create(repo);
  }
  
  public static Exhibition from(ExhibitionCreateParam param){
    Exhibition exhibition = BeanUtil.shadowCopy(param, Exhibition.class);
    
    Date now = new Date();
    if(param.getStartAt().before(now)){
      exhibition.setStatus(ExhibitionStatus.ACTIVE.getValue());
    }else{
      exhibition.setStatus(ExhibitionStatus.INACTIVE.getValue());
    }
    if(param.getEndAt().before(now)){
      exhibition.setStatus(ExhibitionStatus.EXPIRED.getValue());
    }
    return exhibition;
  }
  
  
  public static Integer batchStartExhibitions(ExhibitionRepo repo) {
    return repo.batchStart();
  }
  
  public static Integer batchEndExhibitions(ExhibitionRepo repo){
    return repo.batchEnd();
  }
  
  
  protected Exhibition create(ExhibitionRepo repo){
    repo.create(this);
    return this;
  }
  
  
  public static Exhibition load(ExhibitionRepo repo, Long exhibitionId) {
    return Optional.ofNullable(repo.findById(exhibitionId))
        .orElseThrow(()->new BusinessException("活动不存在"));
  }
  
  
  public static List<Exhibition> page(ExhibitionRepo repo, ExhibitionQueryParam param) {
    return repo.paging(param);
  }
  
  public static Long count(ExhibitionRepo repo, ExhibitionQueryParam param) {
    return repo.count(param);
  }
  
  
  public static Boolean update(ExhibitionRepo repo, ExhibitionUpdateParam param) {
    Exhibition exhibition = load(repo, param.getId());
    if(! Objects.equals(ExhibitionStatus.INACTIVE.getValue(), exhibition.getStatus())){
      throw new BusinessException("会场状态");
    }
    exhibition.merge(param).update(repo);
    return Boolean.TRUE;
  }
  
  public Exhibition merge(ExhibitionUpdateParam param){
    return BeanUtil.merge(this, param);
  }
  
  public Exhibition update(ExhibitionRepo repo){
    repo.update(this);
    return this;
  }
  
  public static Boolean updateStatus(ExhibitionRepo repo, ExhibitionUpdateStatusParam param) {
    load(repo, param.getId()).setStatus(param.getStatus()).update(repo);
    return Boolean.TRUE;
  }

  public static Exhibition copy(ExhibitionRepo repo, ExhibitionCopyParam param){
    Exhibition exhibition = load(repo, param.getExhibitionId()).setStartAt(param.getStartAt()).setEndAt(param.getEndAt());
    exhibition.setId(null);
    exhibition.setStatus(ExhibitionStatus.INACTIVE.getValue());
    exhibition.create(repo);
    return exhibition;
  }

}
