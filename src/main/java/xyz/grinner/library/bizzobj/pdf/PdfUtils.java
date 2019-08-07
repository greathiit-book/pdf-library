package xyz.grinner.library.bizzobj.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 19:31
 */
@Component
public class PdfUtils {

    public static ArrayList<byte[]> getAllPages(File book) throws IOException {
        PDDocument document  = PDDocument.load(book);

        ArrayList<byte[]> imagePages = new ArrayList<>(document.getNumberOfPages());

        for(PDPage page : document.getPages()) {
            PDResources pdResources = page.getResources();
            Iterable<COSName> objectNames = pdResources.getXObjectNames();
            for(COSName XObject : objectNames){
                if(pdResources.isImageXObject(XObject)){
                    PDImageXObject image = (PDImageXObject) pdResources.getXObject(XObject);

                    String format = image.getSuffix();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(image.getImage(), format, out);

                    byte[] imageData = out.toByteArray();
                    imagePages.add(imageData);
                }
            }
        }
        return imagePages;
    }

    public static String getBookContent(File book) throws IOException {
        PDDocument document  = PDDocument.load(book);
        PDFTextStripper stripper=new PDFTextStripper();
        stripper.setSortByPosition(true);
        String content = stripper.getText(document);
        return content;
    }

}
