package top.zhacker.sample.retail.online.item.application;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import top.zhacker.sample.retail.online.item.domain.category.Category;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryCreateParam;
import top.zhacker.sample.retail.online.item.domain.category.CategoryRepo;
import top.zhacker.sample.retail.online.item.application.category.param.CategoryUpdateParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:45
 */
@Validated
@Service
public class CategoryService {
  
  @Autowired
  private CategoryRepo categoryRepo;
  
  public Category findById(Long id){
    return Category.findById(categoryRepo, id);
  }

  @SentinelResource("category-findAll")
  public List<Category> findAll(){
    return Category.findAll(categoryRepo);
  }

  public List<Category> findVisible(){
    return Category.findVisible(categoryRepo);
  }
  
  @Transactional
  public Category create(@Valid CategoryCreateParam param){
    return Category.create(categoryRepo, param);
  }
  
  @Transactional
  public Category update(@Valid CategoryUpdateParam param){
    return Category.update(categoryRepo, param);
  }
}
