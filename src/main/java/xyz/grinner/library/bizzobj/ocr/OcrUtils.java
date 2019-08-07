package xyz.grinner.library.bizzobj.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 18:10
 */
@Component
public class OcrUtils {

    @Autowired
    private static AipOcr ocrClient;

    public static String ocr(byte[] image){
        String ocrResult = null;
        JSONObject result = ocrClient.basicGeneral(image, null);
        if(result.getInt("words_result_num") > 0) {
            JSONArray fragments = result.getJSONArray("words_result");
            ocrResult = fragments.toList().stream().map((Object json)-> {
                return ((JSONObject)json).getString("words");
            }).reduce("",(String a,String b)->a.concat(b));
        }
        return ocrResult;
    }
}
