package top.zhacker.sample.provider.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:41
 */
@Data
@Accessors(chain = true)
public class UserPageRequest {
    private Integer age;
    private Integer pageNo;
    private Integer pageSize;
}
