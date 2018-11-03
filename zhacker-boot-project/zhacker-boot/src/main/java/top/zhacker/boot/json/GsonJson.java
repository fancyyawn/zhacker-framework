package top.zhacker.boot.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;


/**
 * Created by zhacker.
 * Time 2018/8/25 下午2:08
 */
public class GsonJson implements Json {
  
  private static class DateSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date source, Type typeOfSource, JsonSerializationContext context) {
      return new JsonPrimitive(Long.toString(source.getTime()));
    }
  }
  
  private static class DateDeserializer implements JsonDeserializer<Date> {
    public Date deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context) throws JsonParseException {
      long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
      return new Date(time);
    }
  }
  
  final static Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer())
      .registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();
  
  @Override
  public String toJson(Object obj) {
    return gson.toJson(obj);
  }
  
  
  @Override
  public <T> T fromJson(String json, Class<T> clazz) {
    return gson.fromJson(json, clazz);
  }
  
  
  @Override
  public <T> List<T> fromJsonList(String jsonList, Class<T> clazz) {
    return gson.fromJson(jsonList, new TypeToken<List<T>>(){}.getType());
  }
}
