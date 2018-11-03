package top.zhacker.boot.cache.impl;

//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.util.StopWatch;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import top.zhacker.core.exception.BusinessException;


/**
 * Created by zhacker.
 * Time 2018/9/5 上午11:34
 */
@Slf4j
public class MultiLevelCache<K,V> implements top.zhacker.boot.cache.Cache<K,V> {
  
  private CacheProperties<K,V> properties;
  
  private Cache<K,V> caffeineCache;
  
  private RedisCache<K,V> redisCache;
  
//  private ExecutorService executorService = Executors.newFixedThreadPool(10);
  
  public MultiLevelCache(CacheProperties<K,V> properties){
    
    super();
    
    this.properties = properties;
  
    caffeineCache = Caffeine.newBuilder()
        .maximumSize(properties.getMaxSize()/100)
        .expireAfterWrite(Math.max(1,properties.getExpireAfterWrite()/2), properties.getUnit())
        .weakKeys()
        .removalListener((key,value,cause)-> log.info("remove cache key={}, value={}, cause={}", key,value,cause))
        .recordStats()
        .build();
    
    redisCache = new RedisCache<K,V>(
        properties.getJedis(),
        properties.getJson(),
        properties.getValueType(),
        properties.getTopic(),
        properties.getExpireAfterWrite(),
        properties.getUnit()
    );
  }
  
  
  @Override
  public Boolean supports(Class<?> clazz) {
    return Objects.equals(clazz, properties.getValueType());
  }
  
  @Override
  public void put(K key, V value) {
    if(! value.getClass().equals(properties.getValueType())){
      throw new BusinessException("cache.data.type.invalid");
    }
    caffeineCache.put(key, value);
    
    redisCache.put(key,value);
  }
  
  @Override
  public Optional<V> get(K key) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      V value;
      value = caffeineCache.getIfPresent(key);
      if (value != null) {
        log.info("hit in caffeineCache, key={}", key);
        return Optional.of(value);
      }else {
        log.info("missing in caffeineCache, key={}", key);
      }

      value = redisCache.get(key);
      if(value!=null){
        log.info("hit in redisCache, key={}", key);
        caffeineCache.put(key, value);
        return Optional.of(value);
      }else{
        log.info("missing in redisCache, key={}", key);
      }
      
      value = properties.getLoader().apply(key);
      if(value!=null){
        caffeineCache.put(key, value);
        
        final V finalValue = value;
       
        redisCache.put(key,finalValue);
        
        return Optional.of(value);
      }else{
        return Optional.empty();
      }
      
    }finally {
      stopWatch.stop();
      log.info("short={}, detail={}", stopWatch.toString(),stopWatch.prettyPrint());
    }
  }
  
  
  @Override
  public void invalidate(K key) {
    caffeineCache.invalidate(key);
    redisCache.invalidate(key);
  }
  
  
  @Override
  public boolean printStat() {
    log.info("caffeine cache stats={}", caffeineCache.stats());
    log.info("redis cache stat={}", redisCache.stat());
    return true;
  }
}
