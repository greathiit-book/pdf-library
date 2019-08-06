package xyz.grinner.library.ocr;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 17:05
 */
@Data
@ConfigurationProperties(prefix="ocr")//通过前缀提示将配置里面的内容注入到当前Bean的属性上。也可以标注在方法上，需要有对应的setter
public class OcrConfig {
    public static final String APP_ID = "你的 App ID";
    public static final String API_KEY = "你的 Api Key";
    public static final String SECRET_KEY = "你的 Secret Key";
}
