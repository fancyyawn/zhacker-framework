package top.zhacker.sample.retail.online.item.domain.brand;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.zhacker.boot.registry.DomainRegistry;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.BaseEntity;
import top.zhacker.sample.retail.online.item.common.Spec;
import top.zhacker.sample.retail.online.item.domain.brand.checker.BrandLogoChecker;
import top.zhacker.sample.retail.online.item.domain.brand.checker.BrandNameDuplicateChecker;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午9:56
 */
@Data
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@Spec(value = BrandNameDuplicateChecker.class)
public class Brand extends BaseEntity<Brand> {

  @ApiModelProperty("品牌ID")
  private Long id;

  @Size(min=1, max=100, message = "品牌名称字符数限制为[1,100]")
  @ApiModelProperty("品牌名称")
  private String name;

  @Spec(value = BrandLogoChecker.class)
  @Pattern(regexp = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]", message = "logo必须为合法的URL")
  @Size(min=1, max = 128, message = "品牌名称长度必须在1-128字符内")
  @ApiModelProperty("品牌logo，为标准的URL图片格式")
  private String logo;

  @ApiModelProperty("排序分值")
  private Long score;

  @ApiModelProperty("是否隐藏")
  private Boolean hidden;

  @ApiModelProperty("版本号")
  private Long version;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

  protected Brand(){}
  
  
  public Brand(Long id, String name, String logo, Long score, Boolean hidden) {
    this.id = id;
    this.name = name;
    this.logo = logo;
    this.score = score;
    this.hidden = hidden;
    
    version = 0L;
    createdAt = new Date();
    updatedAt = new Date();
    
    this.validate();
  }
  
  public Brand changeName(String name){
    
    Brand toUpdate = new Brand().setId(this.id).setName(name);
    toUpdate.validate("name");
    DomainRegistry.bean(BrandNameDuplicateChecker.class).check(toUpdate);
    
    this.name = name;
    return this;
  }
  
  public Brand changeLogo(String logo){
    
    Brand toUpdate = new Brand().setId(this.id).setLogo(logo);
    toUpdate.validate("logo");
    
    this.logo = logo;
    return this;
  }
  
  public Brand changeScore(Long score){
    
    Brand toUpdate = new Brand().setId(this.id).setScore(score);
    toUpdate.validate("score");
    
    this.score = score;
    return this;
  }
  
  public Brand hidden(){
    
    this.hidden = true;
    return this;
  }
  
  public Brand visible(){
    
    this.hidden = false;
    return this;
  }

  public static Brand load(Long id){
    return Optional.ofNullable(DomainRegistry.repo(BrandRepo.class).findById(id))
        .orElseThrow(()->new BusinessException("品牌不存在"));
  }
}
