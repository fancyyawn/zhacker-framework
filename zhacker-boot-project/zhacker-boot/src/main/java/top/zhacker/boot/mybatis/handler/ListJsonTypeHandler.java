package top.zhacker.boot.mybatis.handler;

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

import top.zhacker.boot.json.GsonJson;
import top.zhacker.boot.json.Json;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午7:02
 */
public class ListJsonTypeHandler<T>  extends BaseTypeHandler<List<T>> {
  
  private Class<T> clazz;
  
  protected Json json = new GsonJson();
  
  public ListJsonTypeHandler(){
    Type genType = this.getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
    this.clazz = (Class<T>)params[0];
  }
  
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<T> obj, JdbcType jdbcType) throws SQLException {
//    ps.setString(i, JSON.toJSONString(obj));
    ps.setString(i, json.toJson(obj));
  }
  
  @Override
  public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return parse(resultSet.getString(s));
  }
  
  
  @Override
  public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return parse(resultSet.getString(i));
  }
  
  
  @Override
  public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return parse(callableStatement.getString(i));
  }
  
  private List<T> parse(String key){
    return key == null ? null : json.fromJsonList(key, clazz);
  }
}
