package xyz.grinner.library.bizzobj.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import xyz.grinner.library.dataobj.dbtable.Library;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/22 18:01
 */
@Mapper
public interface LibraryDao {
    @Select("SELECT * " +
            "FROM library")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "shelves",column = "id",
                    many = @Many(fetchType = FetchType.EAGER,
                                select ="xyz.grinner.library.bizzobj.dao.ShelfDao.getShelf"))
    })
    List<Library> getAllLibraries();

}
