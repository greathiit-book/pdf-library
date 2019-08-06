package xyz.grinner.library.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 19:31
 */
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
}
