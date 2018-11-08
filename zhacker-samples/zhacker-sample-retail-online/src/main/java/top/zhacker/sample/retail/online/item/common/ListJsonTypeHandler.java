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
import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午7:02
 */
public class ListJsonTypeHandler<T>  extends BaseTypeHandler<List<T>> {
  
  private Class<T> clazz;
  
  public ListJsonTypeHandler(){
    Type genType = this.getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
    this.clazz = (Class<T>)params[0];
  }
  
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<T> obj, JdbcType jdbcType) throws SQLException {
    ps.setString(i, JSON.toJSONString(obj));
  }
  
  @Override
  public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
    String key = resultSet.getString(s);
    
    return key == null ? null : JSON.parseArray(key, clazz);
  }
  
  
  @Override
  public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
    String key = resultSet.getString(i);
    return key == null ? null : JSON.parseArray(key, clazz);
  }
  
  
  @Override
  public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    String key = callableStatement.getString(i);
    return key == null ? null : JSON.parseArray(key, clazz);
  }
}
