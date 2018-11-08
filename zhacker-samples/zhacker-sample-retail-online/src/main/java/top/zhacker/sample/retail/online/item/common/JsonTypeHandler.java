package top.zhacker.sample.retail.online.item.common;

import com.alibaba.fastjson.JSON;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午7:02
 */
public class JsonTypeHandler<T>  extends BaseTypeHandler<T> {
  
  private Class<T> clazz;
  
  public JsonTypeHandler(){
    Type genType = this.getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
    this.clazz = (Class<T>)params[0];
  }
  
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T obj, JdbcType jdbcType) throws SQLException {
    ps.setString(i, JSON.toJSONString(obj));
  }
  
  @Override
  public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
    String key = resultSet.getString(s);
    
    return key == null ? null : JSON.parseObject(key, clazz);
  }
  
  
  @Override
  public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
    String key = resultSet.getString(i);
    return key == null ? null : JSON.parseObject(key, clazz);
  }
  
  
  @Override
  public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    String key = callableStatement.getString(i);
    return key == null ? null : JSON.parseObject(key, clazz);
  }
}
