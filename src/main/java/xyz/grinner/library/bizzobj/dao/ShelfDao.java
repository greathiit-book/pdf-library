package xyz.grinner.library.bizzobj.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
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

    @Select({"SELECT *",
            "FROM shelf",
            "where type = #{type}"})
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "directory", column = "directory"),
    })
    List<Shelf> getAllShelves(@Param("type")Use type);

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
            "VALUES(#{shelf.name},#{shelf.type})"})
   @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
   int addShelf(@Param("shelf")Shelf shelf);


    @Select({"SELECT *",
            "FROM shelf",
            "WHERE id = ${id}"})
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
            @Result(property = "directory", column = "directory"),
    })
    Shelf getShelf(@Param("id")int id);


    @Update({
            "UPDATE shelf",
            "SET name = #{newName}",
            "WHERE id = #{id}"

    })
    int renameShelf(@Param("id")int id,@Param("newName")String newName);
}
