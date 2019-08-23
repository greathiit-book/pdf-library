package xyz.grinner.library.bizzobj.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import xyz.grinner.library.dataobj.dbtable.Shelf;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/23 11:09
 */
public interface ShelfDao {
//    @Select("SELECT * " +
//            "FROM shelf " +
//            "WHERE id = #{id}")

   @Select("SELECT sf.* " +
           "FROM shelf sf, library_shelf ls " +
           "WHERE ls.library_id = #{libId} " +
           "AND sf.id = ls.shelf_id")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "type", column = "type"),
    })
   List<Shelf> getShelves(@Param("libId") int libId);
}
