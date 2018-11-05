package top.zhacker.sample.provider.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by zhacker.
 * Time 2018/10/21 下午9:39
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
}
