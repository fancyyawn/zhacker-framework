package top.zhacker.boot.event.stream.dispatch.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import top.zhacker.boot.event.stream.dispatch.AbstractProjection;


/**
 * Created by zhacker.
 * Time 2018/8/26 上午9:58
 */
public abstract class AbstractSqlProjection extends AbstractProjection {
  
  protected void execute(PreparedStatement aStatement) throws Exception {
    try {
      aStatement.executeUpdate();
    } finally {
      aStatement.close();
    }
  }
  
  protected boolean exists(String aQuery, String... anArguments) throws Exception {
    boolean exists = false;
    
    PreparedStatement statement = null;
    ResultSet result = null;
    
    try {
      statement =
          ConnectionProvider
              .connection()
              .prepareStatement(aQuery);
      
      for (int idx = 0; idx < anArguments.length; ++idx) {
        statement.setString(idx+1, anArguments[idx]);
      }
      
      result = statement.executeQuery();
      
      if (result.next()) {
        exists = true;
      }
      
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (Exception e) {
          // ignore
        }
      }
      
      if (statement != null) {
        try {
          statement.close();
        } catch (Exception e) {
          // ignore
        }
      }
    }
    
    return exists;
  }
  
}
