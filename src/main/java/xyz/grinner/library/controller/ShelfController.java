package xyz.grinner.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.grinner.library.dataobj.dbtable.Shelf;
import xyz.grinner.library.dataobj.model.Result;
import xyz.grinner.library.service.ShelfService;
import xyz.grinner.library.single.enums.Use;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/26 17:09
 */
@RestController
@RequestMapping("/shelf")
public class ShelfController {

    @Autowired
    ShelfService shelfService;

    @PostMapping("add")
    public Result createShelf(@RequestParam("libraries")HashSet<Integer> libraries, Shelf shelf){
        return shelfService.addShelf(libraries,shelf);
    }

    @GetMapping("/tree")
    public List<Shelf> libTree(@RequestParam("use") Use type){
        return shelfService.getAllShelves(type);
    }

    @PostMapping("delete")
    public Result destroyShelf(int id){
        return shelfService.deleteShelf(id);
    }


    @PostMapping("/rename")
    public Result renameShelf(int id,String newName){
        return shelfService.renameShelf(id,newName);
    }
}
