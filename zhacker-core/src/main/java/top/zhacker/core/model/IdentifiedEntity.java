package top.zhacker.core.model;

import lombok.*;
import lombok.experimental.Accessors;


/**
 * Created by zhacker.
 * Time 2018/8/25 上午11:34
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public abstract class IdentifiedEntity extends ConcurrencySafeEntity {
  
  private long id = -1L;
  
//  private Integer version;
//
//  private Date createdAt;
//
//  private Date updatedAt;
}
