package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.bizzobj.dao.LibraryDao;
import xyz.grinner.library.bizzobj.dao.ShelfDao;
import xyz.grinner.library.bizzobj.es.EsUtils;
import xyz.grinner.library.dataobj.dbtable.Shelf;
import xyz.grinner.library.dataobj.model.Result;
import xyz.grinner.library.single.enums.Use;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/26 17:10
 */
@Service
public class ShelfService {
    @Autowired
    ShelfDao shelfDao;

    @Autowired
    LibraryDao libraryDao;

    @Autowired
    EsUtils esUtils;

    public List<Shelf> getAllShelves(Use type){
        return shelfDao.getAllShelves(type);
    }

    public Result addShelf(HashSet<Integer> libraries, Shelf shelf){
        boolean created;
        try {
            created = esUtils.creatIndex(shelf.getName());
        } catch (Exception e) {
            created =false;
        }
        if(created){
            int saved = shelfDao.addShelf(shelf);
            if(saved != 0){
                libraries.forEach(libraryId -> {
                    int extension = libraryDao.extend(libraryId,shelf.getId());
                });
            }
        }
        return Result.created(shelf);
    }

    public Result deleteShelf(int id) {
        Shelf shelf = shelfDao.getShelf(id);
        if(shelf != null){
            try {
                esUtils.deleteIndex(shelf.getName());
            } catch (Exception e) {
                return Result.fail("删除索引失败");
            }
        }
        return Result.success();
    }

    public Result renameShelf(int id, String newName) {
        if(shelfDao.renameShelf(id,newName) > 0){
            return Result.success();
        }
        return Result.fail("图书馆改名失败");
    }



}
