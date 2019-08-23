package xyz.grinner.library.dataobj.dbtable;

import lombok.Data;
import xyz.grinner.library.single.enums.Use;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/22 14:59
 */
@Data
public class Library {
    private int id;
    private String name;
    private Use type;//使用目的
    private List<Shelf> shelves;
}
