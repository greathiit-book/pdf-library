package xyz.grinner.library.bizzobj.ocr;

import com.baidu.aip.ocr.AipOcr;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 17:05
 */
@Data
//@ConfigurationProperties(prefix="ocr")//通过前缀提示将配置里面的内容注入到当前Bean的属性上。也可以标注在方法上，需要有对应的setter
//prefix+.+属性 = 配置文件里的prefix.属性才能成功。
@Component
public class OcrConfig {

    @Value("${ocr.appid}")
    private String APP_ID;

    @Value("${ocr.appkey}")
    public String API_KEY;

    @Value("${ocr.secretkey}")
    public String SECRET_KEY;

    @Bean
    private AipOcr getOcrClient(){
        return new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }
}
