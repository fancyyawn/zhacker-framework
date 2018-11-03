package top.zhacker.boot.cache.impl;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.Jedis;
import top.zhacker.boot.cache.Cache;
import top.zhacker.boot.json.FastJson;
import top.zhacker.boot.json.Json;
import top.zhacker.core.model.AssertionConcern;


/**
 * Created by zhacker.
 * Time 2018/9/6 下午6:26
 */
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class CacheProperties<K,V> extends AssertionConcern {
  
  @NotNull(message = "topic不能为空")
  private String topic;
  
  @NotNull
  private long expireAfterWrite = 8L;
  
  private long expireAfterAccess;
  
  @NotNull
  private TimeUnit unit = TimeUnit.MINUTES;
  
  private Jedis jedis;
  
  private Json json = new FastJson();
  
  @NotNull
  private Function<K,V> loader = key-> null;
  
  @NotNull
  private Integer maxSize = 10000;
  
  private Class<V> valueType;
  
  
  public static class CachePropertiesBuilder<K,V>{
    
    private long expireAfterWrite = 8L;
    private TimeUnit unit = TimeUnit.MINUTES;
    private Json json = new FastJson();
    private Function<K,V> loader = key-> null;
    private Integer maxSize = 10000;
  }
  
  public Cache<K,V> toCache(){
    this.validate();
    return new MultiLevelCache<>(this);
  }
}
