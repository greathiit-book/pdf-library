package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.bizzobj.es.EsConfig;
import xyz.grinner.library.bizzobj.es.EsUtils;
import xyz.grinner.library.bizzobj.ocr.OcrUtils;
import xyz.grinner.library.bizzobj.pdf.PdfUtils;
import xyz.grinner.library.dataobj.esdoc.Page;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class BookService {


    @Autowired
    EsUtils esUtils;

    @Autowired
    EsConfig esConfig;

    @Autowired
    OcrUtils ocrUtils;

    public void  stackBooks(String bookPackage){
        File theCat = new File(bookPackage);
        if(theCat.exists()){
            List<File> filesQueue = new ArrayList<>();
            filesQueue.add(theCat);
            for(int i = 0;i < filesQueue.size();i++){
                theCat = filesQueue.get(i);
                System.out.println(theCat);
                if(theCat.isDirectory()){
                    File[] files = theCat.listFiles();
                    if(files != null){
                        Arrays.stream(files).forEach(filesQueue::add);
                    }
                }
                String name = theCat.getName();
                if(name.indexOf(".pdf") != -1){
                    String path = theCat.getAbsolutePath();

                    //识别图片内容
                    ArrayList<byte[]> pages = null;
                    try {
                        pages = PdfUtils.getAllPages(theCat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(pages != null){
                        int pageNumber = 1;
                        Iterator<byte[]> it = pages.iterator();
                        while (it.hasNext()){
                            byte[] page = it.next();
                            String content = ocrUtils.ocr(page);
                            if(content == null){
                                System.out.println(theCat.getAbsolutePath());
                            }else{
//                                Page book = new Page(path,name,pageNumber++,content);
//                                saveBook(book);
                            }
                        }
                    }

                    //提取文字内容
                    String content = null;
                    try {
                        content = PdfUtils.getBookContent(theCat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(content != null){
//                        Page page = new Page(path,name,0,content);
//                        saveBook(page);
                    }
                }
            }

        }
    }

    public List<Page> searchBook(String query){
        return  esUtils.searchBook(query);
    }


    private void saveBook(Page page){
        try {
            esUtils.saveDoc(esConfig.LIBRARY, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

