package xyz.grinner.library.bizzobj.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 16:38
 */

@ConfigurationProperties(prefix="es")//通过前缀提示将配置里面的内容注入到当前Bean的属性上。也可以标注在方法上，需要有对应的setter
public class EsConfig {

    @Value("library")
    public static String LIBRARY;

    @Value("index.shards")
    public static int SHARDS;

    @Value("index.replicas")
    public static int REPLICAS;

    @Value("host")
    private static String HOST;

    @Value("port")
    private static int PORT;


    @Bean
    private RestHighLevelClient getEsClient(){
        HttpHost host = new HttpHost(HOST, PORT, "http");
        RestClientBuilder builder = RestClient.builder(host);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
