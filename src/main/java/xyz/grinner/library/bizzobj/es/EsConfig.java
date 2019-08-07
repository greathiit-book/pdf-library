package xyz.grinner.library.bizzobj.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: chenkai
 * @Date: 2019/8/6 16:38
 */
@Component
//@ConfigurationProperties//通过前缀提示将配置里面的内容注入到当前Bean的属性上。也可以标注在方法上，需要有对应的setter
public class EsConfig {

    @Value("${es.library}")
    public String LIBRARY;

    @Value("${es.index.shards}")
    public int SHARDS;

    @Value("${es.index.replicas}")
    public int REPLICAS;

    @Value("${es.host}")
    private String HOST;

    @Value("${es.port}")//可以放在私有字段上，但是不能放到静态字段上。
    private int PORT;


    @Bean
    private RestHighLevelClient getEsClient(){
        HttpHost host = new HttpHost(HOST, PORT, "http");
        RestClientBuilder builder = RestClient.builder(host);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
