package xyz.grinner.library.bizzobj.dao;

import org.apache.ibatis.annotations.*;
import xyz.grinner.library.dataobj.dbtable.Shelf;
import xyz.grinner.library.single.enums.Use;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/23 11:09
 */
@Mapper
public interface ShelfDao {
//    @Select("SELECT * " +
//            "FROM shelf " +
//            "WHERE id = #{id}")

   @Select({"SELECT sf.*",
           "FROM shelf sf, library_shelf ls",
           "WHERE ls.library_id = #{libId}",
           "AND ls.shelf_id =  sf.id"})
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
    })
   List<Shelf> getShelves(@Param("libId") int libId);

   @Insert({"INSERT INTO shelf",
            "(name,type)",
            "VALUES(#{name},#{type})"})
   int addShelf(@Param("name")String name,@Param("type") Use type);
}
