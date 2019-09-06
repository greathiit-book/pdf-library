package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.bizzobj.dao.LibraryDao;
import xyz.grinner.library.dataobj.dbtable.Library;
import xyz.grinner.library.single.enums.Use;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/26 14:21
 */
@Service
public class LibraryService {

    @Autowired
    LibraryDao libraryDao;

    public List<Library> getAllLibraries(Use type){
        return libraryDao.getAllLibraries(type);
    }
}
