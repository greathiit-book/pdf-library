package xyz.grinner.library.bizzobj.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.io.IOUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.action.ActionListener;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: chenkai
 * @Date: 2019/8/7 10:42
 */
@Component
public class EsUtils {


    @Autowired
    private static RestHighLevelClient client;

    private static IndicesClient indexClient;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    // @PostConstruct和@PreConstruct。这两个注解被用来修饰一个非静态的void()方法
    @PostConstruct
    private void initEs(){
        GetIndexRequest request = new GetIndexRequest(xyz.grinner.library.bizzobj.es.EsConfig.LIBRARY);

        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean exists) {
                if(!exists){
                    try {
                        creatIndex(xyz.grinner.library.bizzobj.es.EsConfig.LIBRARY);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                // TODO: 2019/8/7  日志处理
            }
        };
        indexClient = client.indices();
        indexClient.existsAsync(request,RequestOptions.DEFAULT,listener);
    }

    public void creatIndex(String name) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(name);
        request.settings(Settings.builder()
                .put("index.number_of_shards", xyz.grinner.library.bizzobj.es.EsConfig.SHARDS)
                .put("index.number_of_replicas", xyz.grinner.library.bizzobj.es.EsConfig.REPLICAS)
        );

        InputStream inputStream = EsUtils.class.getResourceAsStream("/es/"+ name +".json");
        byte[] datas =  IOUtils.toByteArray(inputStream);
        String mappingContent = new String(datas);

        request.mapping(mappingContent, XContentType.JSON);

        ActionListener<CreateIndexResponse> listener =
            new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(CreateIndexResponse createIndexResponse) {
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    if(!acknowledged){
                        // TODO 记录创建索引失败
                    }
                    boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
                    if(!shardsAcknowledged){
                        // TODO 记录创建分片失败
                    }

                }

                @Override
                public void onFailure(Exception e) {
                    // TODO: 2019/8/7
                }
        };

        indexClient.createAsync(request, RequestOptions.DEFAULT, listener);
    }

    public void saveDoc(String index,Object doc) throws IOException {

        String content = mapper.writeValueAsString(doc);

        IndexRequest request = new IndexRequest(index);
        request.source(content, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        String docId = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            DocWriteResponse.Result retryResult = client.index(request, RequestOptions.DEFAULT).getResult();
            if(retryResult != DocWriteResponse.Result.CREATED){
                //TODO 记录索引失败信息
            }
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            // TODO: 2019/8/7 记录覆盖信息
        }
    }
}
