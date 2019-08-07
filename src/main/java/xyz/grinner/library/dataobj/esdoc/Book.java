package xyz.grinner.library.dataobj.esdoc;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: chenkai
 * @Date: 2019/8/7 12:04
 */
@Data
@NoArgsConstructor// jackson反序列化需要无参构造函数
public class Book {
    public Book(String path, String name, int page, String content) {
        this.path = path;
        this.name = name;
        this.page = page;
        this.content = content;
    }

    private String path;
    private String name;
    private int page;
    private  String content;
}
