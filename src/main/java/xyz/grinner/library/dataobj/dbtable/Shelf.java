package xyz.grinner.library.dataobj.dbtable;

import lombok.Data;
import xyz.grinner.library.single.enums.Use;

/**
 * @Author: chenkai
 * @Date: 2019/8/22 14:45
 */
@Data
public class Shelf {
    private int id;
    private String name;//索引名称
    private Use type;//使用目的
}
