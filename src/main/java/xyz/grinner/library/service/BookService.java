package xyz.grinner.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.grinner.library.ocr.OcrUtils;
import xyz.grinner.library.pdf.PdfUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    public void  stackBooks(String bookPackage){
        File theCat = new File(bookPackage);
        if(theCat.exists()){
            List<File> filesQueue = new ArrayList<>();
            filesQueue.add(theCat);
            for(int i = 0;i < filesQueue.size();i++){
                if(theCat.isDirectory()){
                    Arrays.stream(theCat.listFiles()).forEach(filesQueue::add);
                }
                String name = theCat.getName();
                if(name.indexOf(".pdf") != -1){
                    String path = theCat.getAbsolutePath();
                    ArrayList<byte[]> pages = null;
                    try {
                        pages = PdfUtils.getAllPages(theCat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(pages != null){
                        pages.stream().forEach((byte[] page)->{
                            String content = OcrUtils.ocr(page);
                            // TODO 存储到ES
                        });
                    }
                }
            }

        }
    }
}
