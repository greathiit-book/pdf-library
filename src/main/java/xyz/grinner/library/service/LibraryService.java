package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.grinner.library.bizzobj.dao.LibraryDao;
import xyz.grinner.library.dataobj.dbtable.Library;
import xyz.grinner.library.dataobj.model.Result;
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


    @Transactional
    public Result deleteLibrary(int id){
        if(libraryDao.deleteLibrary(id) > 0){
            libraryDao.deleteCollection(id);
            return Result.success();
        }
        return Result.fail("图书馆不存在");
    }

    public Result renameLibrary(int id,String newName){
        if(libraryDao.renameLibrary(id,newName) > 0){
            return Result.success();
        }
        return Result.fail("图书馆改名失败");
    }
}
