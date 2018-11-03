package top.zhacker.boot.validate.spec;

/**
 * Created by zhacker.
 * Time 2018/1/5 下午5:47
 */
public interface Checker {
  void check(Object t);
  Class<?> supports();
}
