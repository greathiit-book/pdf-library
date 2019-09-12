package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.bizzobj.dao.LibraryDao;
import xyz.grinner.library.bizzobj.dao.ShelfDao;
import xyz.grinner.library.bizzobj.es.EsUtils;
import xyz.grinner.library.dataobj.dbtable.Shelf;

import java.io.IOException;
import java.util.HashSet;

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

    public Shelf addShelf(HashSet<Integer> libraries, Shelf shelf){
        boolean created;
        try {
            created = esUtils.creatIndex(shelf.getName());
        } catch (IOException e) {
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
        return shelf;
    }

    public Shelf deleteShelf(int id) {
        Shelf shelf = shelfDao.getShelf(id);
        if(shelf != null){
            try {
                esUtils.deleteIndex(shelf.getName());
            } catch (IOException e) {

            }
        }
    }
}
