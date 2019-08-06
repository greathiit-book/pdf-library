package xyz.grinner.library.pdf;

import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 19:31
 */
public class PdfUtils {

    public static void getBook(String path) throws IOException {
        PDDocument document  = PDDocument.load(new File(path));
        for(PDPage page : document.getPages()) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List<Object> tokens = parser.getTokens();

        }
    }
}
