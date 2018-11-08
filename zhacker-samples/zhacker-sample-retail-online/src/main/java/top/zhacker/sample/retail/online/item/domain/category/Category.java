package top.zhacker.sample.retail.online.item.domain.category;

import com.google.common.base.Strings;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.common.BaseEntity;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryCreateParam;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryUpdateParam;


/**
 * Created by zhacker.
 * Time 2018/1/8 下午10:34
 */
@Data
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public class Category extends BaseEntity<Category> {
  
  public final static Long ROOT_ID = 0L;
  
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
  
  protected Category(){}
  
  
  public static Category create(CategoryRepo repo, CategoryCreateParam param){
    if(repo.findByParentIdAndName(param.getParentId(), param.getName())!=null){
      throw new BusinessException("分类重名");
    }
    return from(param).create(repo);
  }

  
  public static Category from(CategoryCreateParam param){
    return from(param, Category.class).setLeaf(true);
  }
  
  public Category create(CategoryRepo repo){
    repo.create(this);
    return this;
  }
  
  public static Category update(CategoryRepo repo, CategoryUpdateParam param){
    Category category = load(repo, param.getId());

    if(! Strings.isNullOrEmpty(param.getName()) && ! Objects.equals(category.getName(), param.getName())){
      if(repo.findByParentIdAndName(category.getParentId(), param.getName())!=null){
        throw new BusinessException("分类重名");
      }
    }
    return category.merge(param).update(repo);
  }
  public Category update(CategoryRepo repo){
    repo.update(this);
    return this;
  }
  
  public static Category load(CategoryRepo repo, Long id){
    return Optional.ofNullable(repo.findById(id)).orElseThrow(()->new BusinessException("分类不存在"));
  }
  
  public static List<Category> findAll(CategoryRepo repo){
    return repo.findAll();
  }
  
  
  public static Category findById(CategoryRepo categoryRepo, Long id) {
    return categoryRepo.findById(id);
  }


  public static List<Category> findVisible(CategoryRepo repo) {
    return repo.findVisible();
  }
}
