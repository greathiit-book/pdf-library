package xyz.grinner.library.bizzobj.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.io.IOUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.grinner.library.dataobj.esdoc.Page;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: chenkai
 * @Date: 2019/8/7 10:42
 */
@Component
public class EsUtils {


    @Autowired
    EsConfig esConfig;

    @Autowired
    private RestHighLevelClient client;

    private IndicesClient indexClient;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    // @PostConstruct和@PreConstruct。这两个注解被用来修饰一个非静态的void()方法
    @PostConstruct
    private void buildLibrary(){
        GetIndexRequest request = new GetIndexRequest(esConfig.LIBRARY);

        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean exists) {
                if(!exists){
                    try {
                        creatIndex(esConfig.LIBRARY);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                // TODO: 2019/8/7  日志处理
                e.printStackTrace();
                System.out.println();
            }
        };
        indexClient = client.indices();
        indexClient.existsAsync(request,RequestOptions.DEFAULT,listener);
    }

    public boolean creatIndex(String name) throws IOException {
        InputStream inputStream = EsUtils.class.getResourceAsStream("/es/page.json");
        byte[] datas =  IOUtils.toByteArray(inputStream);
        String mappingContent = new String(datas);

        CreateIndexRequest request = new CreateIndexRequest(name);
        request.mapping(mappingContent, XContentType.JSON);
        request.setTimeout(TimeValue.timeValueSeconds(5));
        request.settings(Settings.builder()
                .put("index.number_of_shards", esConfig.SHARDS)
                .put("index.number_of_replicas", esConfig.REPLICAS)
        );

        CreateIndexResponse createIndexResponse = indexClient.create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        return acknowledged;
    }

    public boolean deleteIndex(String name) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
        deleteIndexRequest.indices(name);
        AcknowledgedResponse deleteIndexResponse = indexClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return deleteIndexResponse.isAcknowledged();
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

    public List<Page> searchBook(String query){
        List<Page> pages = new ArrayList<>();
        // 1.书名包含关键字
        // 2.内容包含关键字
        QueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", query))
                .should(QueryBuilders.matchQuery("content", query));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest(esConfig.LIBRARY);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null){
            // 解析结果
            SearchHits hits = response.getHits();
            SearchHit[] hitsArray = hits.getHits();
            Arrays.stream(hitsArray).forEach((SearchHit hit)->{
                String sourceAsString = hit.getSourceAsString();
                try {
                    Page page = mapper.readValue(sourceAsString, Page.class);
                    pages.add(page);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return pages;
    }
}
