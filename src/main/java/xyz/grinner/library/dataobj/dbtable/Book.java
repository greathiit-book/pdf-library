package xyz.grinner.library.dataobj.dbtable;

import lombok.Data;

/**
 * @Author: chenkai
 * @Date: 2019/8/22 14:24
 */
@Data
public class Book {//数据库表
    private int id;
    private String path;//文件绝对路径，用于定义文件
    private String index;//存在的索引。
    private String md5;//文件标识
}
/**
 *     //使用类型：1.图书馆 2.文档搜索 3.单词背诵
 *
 * 1.索引库用于存储图书，每个索引库类似于一个图书架，或者一个一次文档搜索的目标库，或者一个系列剧的一季的字幕。
 * 可以建立索引库，也可以指定索引库。可以为原有索引库添加内容。索引库具有类型属性。
 *
 * 每次索引文件，勾选要添加到的索引，记录当前文件、当前索引类型。当同文件插入到同一索引库时，会提示已经存在。
 * 2.组合使用索引库，形成图书馆、形成组合文档搜索范围，形成整部剧。
 * 3.每种使用类型的书，放在不同索引库，每次添加一个库，或者选择存储到之前的库。
 * 1.图书馆内存在过的文件，不会再存一次。
 *
 * 搜索：所有的建立索引时，保留索引的主目录，
 * 如果新建的索引包括了之前的目录，有一个可选项，是否排除已索引过的文件夹。会自动把之前的目录包括进来。
 * 所以需要一个图书馆的概念。
 * 图书馆总是保存新的索引，在前端提供通过其他馆来选取新索引的功能，
 * 文档搜索去索引文件夹的时候，总是判断当前文件夹是否已经被原有的索引保留，然后会把旧索引加到新索引里，使之成为新的管子。
 *
 * 1.Shelf代表一个书架，等同于ES一个索引
 * 2.Book代表书架上的好多本书，等同于加入到索引的每个磁盘文件，
 * 同一个书籍上不能有相同的书，同一个索引内不能索引同一文件
 * 3.Pages代表书中的一页内容，等同于Es一个Document
 * 4.Library代表一个由多书架组成的图书馆，通过组合，代表各种搜索范围。library可以由其它library组成，相当于分馆
 * 但是library里不能存在相同的Shelf
 *
 * /
