package top.zhacker.core.model;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/25 上午11:34
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
public abstract class IdentifiedEntity extends AssertionConcern {
  
  private long id = -1L;
  
  private Integer version;
  
  private Date createdAt;
  
  private Date updatedAt;
}
