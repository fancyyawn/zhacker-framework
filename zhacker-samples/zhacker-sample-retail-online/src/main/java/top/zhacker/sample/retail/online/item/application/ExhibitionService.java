package top.zhacker.sample.retail.online.item.application;


import top.zhacker.core.exception.BusinessException;
import top.zhacker.sample.retail.online.item.domain.exhibition.*;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionCopyParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionCreateParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionQueryParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionUpdateParam;
import top.zhacker.sample.retail.online.item.application.exhibition.param.ExhibitionUpdateStatusParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.*;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemAddParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemDeleteParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemQueryParam;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.param.ExhibitionItemUpdateParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by zhacker.
 * Time 2018/1/27 下午6:11
 */
@Service
public class ExhibitionService {
  
  @Autowired
  private ExhibitionRepo exhibitionRepo;
  
  @Autowired
  private ExhibitionItemRepo exhibitionItemRepo;
  
  @Autowired
  private ItemService itemService;
  
  @Transactional
  public Exhibition create(ExhibitionCreateParam param){
    return Exhibition.create(exhibitionRepo, param);
  }
  
  @Transactional
  public Boolean update(ExhibitionUpdateParam param){
    return Exhibition.update(exhibitionRepo, param);
  }
  
  public Exhibition findById(Long exhibitionId){
    return Exhibition.load(exhibitionRepo, exhibitionId);
  }
  
  public List<Exhibition> page(ExhibitionQueryParam param){
    return Exhibition.page(exhibitionRepo, param);
  }
  
  public Long count(ExhibitionQueryParam param){
    return Exhibition.count(exhibitionRepo, param);
  }
  
  public Boolean updateStatus(ExhibitionUpdateStatusParam param) {
    return Exhibition.updateStatus(exhibitionRepo, param);
  }
  
  public Integer batchStartExhibitions(){
    return Exhibition.batchStartExhibitions(exhibitionRepo);
  }
  
  public Integer batchEndExhibitions(){
    return Exhibition.batchEndExhibitions(exhibitionRepo);
  }
  
  @Transactional
  public Boolean addItems(ExhibitionItemAddParam param){
    //todo check valid add
    Exhibition exhibition = Exhibition.load(exhibitionRepo, param.getExhibitionId());

    for(Long itemId : param.getItemIds()){

      ExhibitionItemQueryParam queryParam = new ExhibitionItemQueryParam()
          .setType(exhibition.getType())
          .setItemId(itemId)
          .setStartAt(exhibition.getStartAt())
          .setEndAt(exhibition.getEndAt());

      List<ExhibitionItem> exists = exhibitionItemRepo.findBy(queryParam);

      if(! exists.isEmpty()){
        throw new BusinessException("商品已经添加到了活动中");
      }
    }

    ExhibitionItem.create(exhibitionItemRepo, itemService, param);
    return Boolean.TRUE;
  }
  
  @Transactional
  public Boolean removeItems(ExhibitionItemDeleteParam param){
    ExhibitionItem.delete(exhibitionItemRepo, param);
    return Boolean.TRUE;
  }
  
  @Transactional
  public Boolean updateItem(ExhibitionItemUpdateParam param){
    ExhibitionItem.update(exhibitionItemRepo, param);
    return Boolean.TRUE;
  }

  /**
   * 复制活动
   *
   * @param param
   * @return 失败的活动商品ID
   */
  @Transactional
  public List<ExhibitionItem> copy(ExhibitionCopyParam param){

    Exhibition exhibition = Exhibition.load(exhibitionRepo, param.getExhibitionId());
    List<ExhibitionItem> items = exhibitionItemRepo.findByExhibitionId(param.getExhibitionId());

    List<ExhibitionItem> failItems = new ArrayList<>();
    List<ExhibitionItem> validItems = new ArrayList<>();

    for(ExhibitionItem item : items){

      ExhibitionItemQueryParam queryParam = new ExhibitionItemQueryParam()
          .setType(exhibition.getType())
          .setItemId(item.getItemId())
          .setStartAt(param.getStartAt())
          .setEndAt(param.getEndAt());

      List<ExhibitionItem> exists = exhibitionItemRepo.findBy(queryParam);

      if(exists.isEmpty()) {
        validItems.add(item);
      }else{
        failItems.addAll(exists.stream().collect(Collectors.toList()));
      }
    }

    Exhibition exhibitionCopied = Exhibition.copy(exhibitionRepo, param);
    validItems.forEach(item-> item.copy(exhibitionItemRepo, exhibitionCopied.getId()));
    return failItems;
  }


  public List<ExhibitionItem> query(ExhibitionItemQueryParam param){
    return exhibitionItemRepo.findBy(param);
  }

  
}
