package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.bizzobj.dao.LibraryDao;
import xyz.grinner.library.bizzobj.dao.ShelfDao;
import xyz.grinner.library.bizzobj.es.EsUtils;
import xyz.grinner.library.single.enums.Use;

import java.io.IOException;

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

    public void addShelf(String shelfName,int libId,Use type){
        boolean created;
        try {
            created = esUtils.creatIndex(shelfName);
        } catch (IOException e) {
            created =false;
        }
        if(created){
            int shelfId = shelfDao.addShelf(shelfName, type);
            if(shelfId != 0){
                int extension = libraryDao.extend(libId,shelfId);
            }
        }
        return ;
    }
}
