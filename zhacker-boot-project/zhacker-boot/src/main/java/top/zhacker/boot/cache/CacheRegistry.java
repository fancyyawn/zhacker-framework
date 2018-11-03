package top.zhacker.boot.cache;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/9/5 上午11:45
 */
public class CacheRegistry {
  
  @Autowired
  List<Cache> multiLevelCaches = new ArrayList<>();
  
}
