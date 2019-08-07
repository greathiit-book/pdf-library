package xyz.grinner.library.bizzobj.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 18:10
 */
@Component
public class OcrUtils {

    @Autowired
    private AipOcr ocrClient;

    public String ocr(byte[] image){
        String ocrResult = null;
        JSONObject result = ocrClient.basicGeneral(image, null);
        if(result.getInt("words_result_num") > 0) {
            JSONArray fragments = result.getJSONArray("words_result");
             List<Object> wordsList= fragments.toList();
             ocrResult =  wordsList.stream().map((Object json) -> {
               HashMap<String, String> jsonObj = (HashMap<String, String>)json;
               String words = jsonObj.get("words");
               return words;
            }).reduce("",(String a,String b)->a.concat(b));
        }
        return ocrResult;
    }
}
