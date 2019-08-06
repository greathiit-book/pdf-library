package xyz.grinner.library.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 18:10
 */
public class OcrUtils {

    @Autowired
    private static AipOcr ocrClient;

    public static void ocr(byte[] image){
        JSONObject result = ocrClient.basicGeneral(image, null);
    }
}
