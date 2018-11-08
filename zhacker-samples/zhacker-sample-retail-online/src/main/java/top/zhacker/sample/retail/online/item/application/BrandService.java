package top.zhacker.sample.retail.online.item.application;

import top.zhacker.boot.idgen.IdGen;
import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.brand.Brand;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandCreateParam;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandDeleteParam;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandQueryParam;
import top.zhacker.sample.retail.online.item.domain.brand.BrandRepo;
import top.zhacker.sample.retail.online.item.application.brand.param.BrandUpdateParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


/**
 * Created by zhacker.
 * Time 2018/1/10 下午9:24
 */
@Validated
@Service
public class BrandService {
  
  @Autowired
  private BrandRepo brandRepo;
  
  @Autowired
  private IdGen idGen;
  
  @Transactional
  public Brand create(@Valid BrandCreateParam param){
    
    Brand brand = new Brand(
        idGen.generateId(),
        param.getName(),
        param.getLogo(),
        param.getScore(),
        param.getHidden()
    );
    
    brandRepo.create(brand);
    
    return brand;
  }
  
  
  public Brand get(Long id) {
    return Brand.load(id);
  }
  
  @Transactional
  public Brand update(@Valid BrandUpdateParam param){
    
    Brand brand = Brand.load(param.getId());
    
    Optional.ofNullable(param.getName()).ifPresent(brand::changeName);
    Optional.ofNullable(param.getLogo()).ifPresent(brand::changeLogo);
    Optional.ofNullable(param.getScore()).ifPresent(brand::changeScore);
    Optional.ofNullable(param.getHidden()).ifPresent(hidden->{
      if(hidden){
        brand.hidden();
      }else{
        brand.visible();
      }
    });
    
    brandRepo.update(brand);
    return brand;
  }
  
  @Transactional
  public Boolean delete(@Valid BrandDeleteParam param){
    brandRepo.delete(param.getId());
    return true;
  }
  
  public Page<Brand> page(BrandQueryParam param){
    return new Page<>(count(param), list(param));
  }
  
  public List<Brand> list(BrandQueryParam param){
    return brandRepo.page(param);
  }
  
  public Long count(BrandQueryParam param){
    return brandRepo.count(param);
  }
  
}
