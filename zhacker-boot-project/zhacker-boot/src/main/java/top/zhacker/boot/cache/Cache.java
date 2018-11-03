package top.zhacker.boot.cache;

import java.util.Optional;


/**
 * Created by zhacker.
 * Time 2018/9/5 上午11:28
 */
public interface Cache<K,V> {
  Boolean supports(Class<?> clazz);
  void put(K key, V value);
  Optional<V> get(K key);
  void invalidate(K key);
  boolean printStat();
}
