package top.zhacker.boot.leveldb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.iq80.leveldb.DB;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by zhacker.
 * Time 2018/7/7 上午9:57
 */
@Aspect
public class LevelDbTransactionalAspect {
  
  @Value("${leveldb.path:/tmp/leveldb}")
  private String databasePath;
  
  private DB db;
  
  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(Paths.get(databasePath));
    db = LevelDBProvider.instance().databaseFrom(databasePath);
  }
  
  @Around("@annotation(top.zhacker.boot.leveldb.LevelDbTransactional)")
  public Object doRequestMethod(ProceedingJoinPoint pjp) throws Throwable {
    return process(pjp);
  }
  
  Object process(ProceedingJoinPoint pjp) throws Throwable {
    try{
      LevelDBUnitOfWork.start(db);
      
      Object result = pjp.proceed();
  
      LevelDBUnitOfWork.current().commit();
      
      return result;
    }catch (Throwable e){
      LevelDBUnitOfWork.current().rollback();
      throw e;
    }
  }
}
