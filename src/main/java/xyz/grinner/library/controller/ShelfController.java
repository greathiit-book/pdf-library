package xyz.grinner.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.grinner.library.dataobj.dbtable.Shelf;
import xyz.grinner.library.service.ShelfService;

import java.util.HashSet;

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
    public Shelf createShelf(@RequestParam("libraries")HashSet<Integer> libraries, Shelf shelf){
        return shelfService.addShelf(libraries,shelf);
    }

    @PostMapping("delete")
    public Shelf destroyShelf(int id){
        return shelfService.deleteShelf(id);
    }
}
