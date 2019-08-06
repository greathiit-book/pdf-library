package xyz.grinner.library.es;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 16:38
 */

@ConfigurationProperties(prefix="es")//通过前缀提示将配置里面的内容注入到当前Bean的属性上。也可以标注在方法上，需要有对应的setter
public class EsConfig {

}
