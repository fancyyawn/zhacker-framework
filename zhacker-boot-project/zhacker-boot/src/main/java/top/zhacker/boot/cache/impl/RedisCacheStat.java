package top.zhacker.boot.cache.impl;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/9/6 下午9:45
 */
@Data
@Accessors(chain = true)
public class RedisCacheStat {
  Long hitCount;
  Long missCount;
}
