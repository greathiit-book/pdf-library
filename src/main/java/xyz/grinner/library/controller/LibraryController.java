package xyz.grinner.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.grinner.library.dataobj.dbtable.Library;
import xyz.grinner.library.dataobj.esdoc.Page;
import xyz.grinner.library.service.BookService;
import xyz.grinner.library.service.LibraryService;
import xyz.grinner.library.single.enums.Use;

import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/7 13:22
 */
@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    BookService bookService;

    @Autowired
    LibraryService libraryService;

    @GetMapping("/management/stacking")
    public void stackBooks(@RequestParam String bookPackage){
        bookService.stackBooks(bookPackage);
    }


    @GetMapping("/book/finding")
    public List<Page> findTheCat(@RequestParam String info){
        return  bookService.searchBook(info);
    }


    @GetMapping("/tree")
    public List<Library> libTree(@RequestParam("use") Use type){
        return libraryService.getAllLibraries(type);
    }
}
