package top.zhacker.core.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by zhacker.
 * Time 2018/11/13 上午10:40
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public abstract class ConcurrencySafeEntity extends AssertionConcern {

    private Integer version;

    private Date createdAt;

    private Date updatedAt;
}
