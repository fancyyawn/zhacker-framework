package top.zhacker.boot.mybatis.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import top.zhacker.boot.json.GsonJson;
import top.zhacker.boot.json.Json;


/**
 * Created by zhacker.
 * Time 2018/1/9 下午7:02
 */
public class JsonTypeHandler<T>  extends BaseTypeHandler<T> {
  
  private Class<T> clazz;
  
  
  protected List<SerializerFeature> serializerFeatures(){
    return Collections.emptyList();
  }
  protected Json json = new GsonJson();
  
  public JsonTypeHandler(){
    Type genType = this.getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
    this.clazz = (Class<T>)params[0];
  }
  
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T obj, JdbcType jdbcType) throws SQLException {
    //ps.setString(i, JSON.toJSONString(obj, serializerFeatures().toArray(new SerializerFeature[]{})));
    ps.setString(i, json.toJson(obj));
  }
  
  @Override
  public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return parse(resultSet.getString(s));
  }
  
  
  @Override
  public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return parse(resultSet.getString(i));
  }
  
  
  @Override
  public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return parse(callableStatement.getString(i));
  }
  
  private T parse(String key){
    //    return key == null ? null : JSON.parseObject(key, clazz);
    return key == null ? null : json.fromJson(key, clazz);
  }
}
