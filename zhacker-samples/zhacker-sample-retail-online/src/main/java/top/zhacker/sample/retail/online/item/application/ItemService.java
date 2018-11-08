package top.zhacker.sample.retail.online.item.application;

import top.zhacker.sample.retail.online.item.domain.brand.BrandRepo;
import top.zhacker.sample.retail.online.item.domain.category.CategoryRepo;
import top.zhacker.sample.retail.online.item.domain.item.Item;
import top.zhacker.sample.retail.online.item.domain.item.ItemManager;
import top.zhacker.sample.retail.online.item.domain.item.ItemRepo;
import top.zhacker.sample.retail.online.item.domain.item.checker.ItemCheckers;
import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetail;
import top.zhacker.sample.retail.online.item.domain.item.detail.ItemDetailRepo;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItem;
import top.zhacker.sample.retail.online.item.domain.item.exhibit.ExhibitionItemRepo;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemPageParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateParam;
import top.zhacker.sample.retail.online.item.domain.item.param.ItemUpdateStatusParam;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSku;
import top.zhacker.sample.retail.online.item.domain.item.sku.ItemSkuRepo;
import top.zhacker.sample.retail.online.item.domain.item.snapshot.ItemSnapshot;
import top.zhacker.sample.retail.online.item.domain.item.snapshot.ItemSnapshotCreateParam;
import top.zhacker.sample.retail.online.item.domain.item.snapshot.ItemSnapshotRepo;
import top.zhacker.sample.retail.online.item.util.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by zhacker.
 * Time 2018/1/11 下午8:54
 */
@Validated
@Service
@Slf4j
public class ItemService {

  @Autowired
  @Setter
  private ItemManager itemManager;

  @Autowired
  @Setter
  private ItemRepo itemRepo;

  @Autowired
  @Setter
  private ItemSkuRepo skuRepo;

  @Autowired
  @Setter
  private ItemDetailRepo detailRepo;

  @Autowired
  @Setter
  private BrandRepo brandRepo;

  @Autowired
  @Setter
  private CategoryRepo categoryRepo;

  @Autowired
  @Setter
  private ExhibitionItemRepo exhibitionItemRepo;

  @Autowired
  @Setter
  private ItemSnapshotRepo itemSnapshotRepo;

  @Autowired
  @Setter
  private ItemCheckers itemCheckers;


  @Transactional
  public Item create(@Valid ItemCreateParam param){
    return Item.create(itemManager, param, itemCheckers);
  }

  public Item loadWithSkuAndDetail(Long id){
    return Item.load(itemRepo,id)
        .loadDetail(detailRepo)
        .loadSkus(skuRepo);
  }

  public Item loadExhibitionItem(Long itemId, Long exhibitionId){
    Item item = loadWithSkuAndDetail(itemId);
    if(exhibitionId!=null && ! Objects.equals(exhibitionId , 0L)) {
      ExhibitionItem exhibitionItem = exhibitionItemRepo.findByExhibitionIdAndItemId(exhibitionId, itemId);
      item.merge(exhibitionItem);
    }
    return item;
  }

  public Item loadAll(Long id){
    return Item.load(itemRepo,id)
        .loadDetail(detailRepo)
        .loadSkus(skuRepo)
        .loadBrand(brandRepo)
        .loadCategory(categoryRepo);
  }

  public List<Item> page(ItemPageParam pageParam){
    pageParam.loadBrands(brandRepo).loadItemIdsForExhibit(exhibitionItemRepo);

    if(pageParam.getExhibitionId()!=null && pageParam.getItemIds().isEmpty()){
      return Collections.emptyList();
    }

    List<Item> itemPage = Item.page(itemRepo, pageParam);

    if(Objects.equals(pageParam.getLoadCategoryAndBrand(), true)){
      itemPage.forEach(item-> item.loadCategory(categoryRepo).loadBrand(brandRepo));
    }

    if(pageParam.getExhibitionId()!=null) {
      itemPage.forEach(item -> item.merge(ExhibitionItem.load(exhibitionItemRepo, pageParam.getExhibitionId(), item.getId())));
    }
    return itemPage;
  }

  public Long count(ItemPageParam pageParam){

    pageParam.loadBrands(brandRepo).loadItemIdsForExhibit(exhibitionItemRepo);

    if(pageParam.getExhibitionId()!=null && pageParam.getItemIds().isEmpty()){
      return 0L;
    }

    return Item.count(itemRepo, pageParam);
  }

  @Transactional
  public Boolean update(@Valid ItemUpdateParam param){
    Item.updateBasic(itemRepo, param, itemCheckers);
    ItemSku.batchUpdate(skuRepo, param.getId(), param.getSkus());
    ItemDetail.update(detailRepo, param.getDetail());
    return Boolean.TRUE;
  }

  @Transactional
  public Boolean updateStatus(@Valid ItemUpdateStatusParam param){
    return Item.updateStatus(itemRepo, param);
  }

  @Transactional
  public Long createSnapshot(Long itemId){
    ItemSnapshotCreateParam createParam = new ItemSnapshotCreateParam().setItemId(itemId);
    return ItemSnapshot.create(itemSnapshotRepo, this, createParam);
  }

  public ItemSnapshot findSnapshot(Long snapshotId){
    ItemSnapshot snapshot =  ItemSnapshot.load(itemSnapshotRepo, snapshotId);
    return snapshot;
  }

  @Transactional
  public Long copy(Long itemId) {
    Item item = loadWithSkuAndDetail(itemId);
    ItemCreateParam createParam = BeanUtil.deepCopy(item, ItemCreateParam.class);
    Item itemCopied = create(createParam);
    return itemCopied.getId();
  }
}
