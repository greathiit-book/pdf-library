package xyz.grinner.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.grinner.library.service.ShelfService;
import xyz.grinner.library.single.enums.Use;

/**
 * @Author: chenkai
 * @Date: 2019/8/26 17:09
 */
@Controller
public class ShelfController {

    @Autowired
    ShelfService shelfService;

    @PostMapping("")
    public void createShelf(@RequestParam("name")String name,
                            @RequestParam("library")int libId,
                            @RequestParam("type") Use type, BindingResult bindingResult){
        shelfService.addShelf(name,libId,type);
    }
}
