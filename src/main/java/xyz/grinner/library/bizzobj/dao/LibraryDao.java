package xyz.grinner.library.bizzobj.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import xyz.grinner.library.dataobj.dbtable.Library;
import xyz.grinner.library.single.enums.Use;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/22 18:01
 */
@Mapper
public interface LibraryDao {
    @Select({"SELECT *",
            "FROM library",
            "where type = #{type}"})
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "shelves",column = "id",
                    many = @Many(fetchType = FetchType.EAGER,
                                select ="xyz.grinner.library.bizzobj.dao.ShelfDao.getShelves"))
    })
    List<Library> getAllLibraries(@Param("type")Use type);

    @Insert({
            "INSERT INTO library_shelf",
            "VALUES(#{libId},#{shelfId})"
    })
    int extend(@Param("libId") int libId,@Param("shelfId") int shelfId);


    @Delete({
            "DELETE FROM library",
            "WHERE id = #{id}"
    })
    int deleteLibrary(@Param("id")int libId);

    @Delete({
            "DELETE FROM library_shelf",
            "WHERE library_id = #{id}"
    })
    int deleteCollection(@Param("id")int libId);

    @Update({
            "UPDATE library",
            "SET name = #{newName}",
            "WHERE id = #{id}"

    })
    int renameLibrary(@Param("id")int id,@Param("newName")String newName);
}
