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
public class Page {
    public Page(String bookPath, int page, String content) {
        this.bookPath = bookPath;
        this.page = page;
        this.content = content;
    }

    private String bookPath;
    private String fileType;//文件扩展名
    private String dataForm;//数据源，如PDF文件中的图片识别，word中的图片识别
    private int page;//在文件中的大概页码
    private String content;//文本内容
}
