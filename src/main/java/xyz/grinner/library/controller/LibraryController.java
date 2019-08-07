package xyz.grinner.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.grinner.library.service.BookService;

/**
 * @Author: chenkai
 * @Date: 2019/8/7 13:22
 */
@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    BookService bookService;

    @GetMapping("/management/stacking")
    public void stackBooks(@RequestParam String bookPackage){
        bookService.stackBooks(bookPackage);
    }


    @GetMapping("/book/finding")
    public void findTheCat(@RequestParam String info){

    }
}
