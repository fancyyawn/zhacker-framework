package top.zhacker.sample.retail.online.item.application;

import top.zhacker.sample.retail.online.item.common.Page;
import top.zhacker.sample.retail.online.item.domain.item.search.ItemSearch;
import top.zhacker.sample.retail.online.item.domain.item.search.ItemSearchPageParam;
import top.zhacker.sample.retail.online.item.domain.item.search.ItemSearchRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by zhanghecheng on 2018/3/14.
 */
@Service
public class ItemSearchService {

  @Autowired
  private ItemSearchRepo searchRepo;

  public void addSearch(String field, String keyword){
    ItemSearch search = searchRepo.findByFieldAndKeyWord(field,keyword);
    if(search == null){
      searchRepo.create(new ItemSearch().setKeyword(keyword).setField(field).setCount(1L));
    }else{
      searchRepo.update(search.setCount(search.getCount()+1));
    }
  }

  public Page<ItemSearch> page(ItemSearchPageParam param){
    return new Page<>(searchRepo.count(param), searchRepo.page(param));
  }
}
