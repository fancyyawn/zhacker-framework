package top.zhacker.boot.registry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Setter;


/**
 * Created by zhacker.
 * Time 2018/8/25 下午4:37
 */
public class DomainRegistry implements ApplicationContextAware {

  @Setter
  private static ApplicationContext applicationContext;

  public static <T> T bean(Class<T> clazz){
    if(applicationContext== null){
      throw new RuntimeException("bean.not.exist");
    }
    Map beans = applicationContext.getBeansOfType(clazz);
    T t = (T)beans.values().stream().findFirst().orElse(null );

    if(t==null){
      throw new RuntimeException("bean.not.exist");
    }

    return t;
  }
  
  public static <T> T service(Class<T> clazz){
    return bean(clazz);
  }

  
  public static <T> T repo(Class<T> clazz){
    return bean(clazz);
  }
  
  
  public static <T> List<T> allBeans(Class<T> clazz){
    if(applicationContext== null){
      throw new RuntimeException("bean.not.exist");
    }
    Map<String,T> beans = applicationContext.getBeansOfType(clazz);
    List<T> t = beans.values().stream().collect(Collectors.toList());
    
    if(t==null){
      throw new RuntimeException("bean.not.exist");
    }
    return t;
  }
  
  public static <T> Map<String, T> beanMap(Class<T> clazz){
    if(applicationContext== null){
      throw new RuntimeException("bean.not.exist");
    }
    Map<String,T> beans = applicationContext.getBeansOfType(clazz);
    
    if(beans==null){
      throw new RuntimeException("bean.not.exist");
    }
    return beans;
  }
  
  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if(DomainRegistry.applicationContext == null){
      DomainRegistry.applicationContext = applicationContext;
    }
  }
}
