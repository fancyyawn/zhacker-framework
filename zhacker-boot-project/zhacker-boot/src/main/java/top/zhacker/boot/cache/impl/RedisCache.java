package top.zhacker.boot.cache.impl;

import com.google.common.primitives.Longs;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import top.zhacker.boot.json.Json;


/**
 * Created by zhacker.
 * Time 2018/9/6 下午6:05
 */
public class RedisCache<K,V> {
  
  private Jedis jedis;
  
  private Json json;
  
  private String topic;
  
  private long ttl;
  
  private TimeUnit unit;
  
  private Class<? extends V> dataType;
  
  private Random random = new Random();
  
  
  public RedisCache(Jedis jedis, Json json, Class<? extends V> dataType, String topic, long ttl, TimeUnit unit) {
    this.topic = topic;
    this.ttl = ttl;
    this.unit = unit;
    this.json = json;
    this.jedis = jedis;
    this.dataType = dataType;
  }
  
  private String cacheKey(K key){
    return "cache_"+topic+"_"+key;
  }
  
  
  private String cacheHitKey(){
    return "hit_cache_"+topic;
  }
  
  private String cacheMissKey(){
    return "miss_cache_"+topic;
  }
  
  
  public void invalidate(K key) {
    if(jedis!=null){
      jedis.del(cacheKey(key));
    }
  }
  
  
  public void put(K key, V value){
    if(jedis!=null) {
      int expire = (int)unit.toSeconds(ttl);
      expire = expire + random.nextInt(4) - 2; //前后随机化，避免大量key同时失效
      jedis.setex(cacheKey(key), expire, json.toJson(value));
    }
  }
  
  public V get(K key){
    if(jedis==null){
      return null;
    }
    String jsonValue = jedis.get(cacheKey(key));
    if(jsonValue!=null) {
      jedis.incr(cacheHitKey());
      return json.fromJson(jsonValue, dataType);
    }else{
      jedis.incr(cacheMissKey());
      return null;
    }
  }
  
  public RedisCacheStat stat(){
    return new RedisCacheStat()
        .setHitCount(Longs.tryParse(Optional.ofNullable(jedis.get(cacheHitKey())).orElse("0")))
        .setMissCount(Longs.tryParse(Optional.ofNullable(jedis.get(cacheMissKey())).orElse("0")));
  }
  
}
