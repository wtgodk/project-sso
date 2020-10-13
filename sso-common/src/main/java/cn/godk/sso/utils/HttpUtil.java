package cn.godk.sso.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http client tool
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-21  10:16
 */
@Slf4j
public class HttpUtil {


    public static <T> T doPostJson(String url, Object param, TypeReference<T> type) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity s = new StringEntity(JSON.toJSONString(param));
            s.setContentEncoding("UTF-8");
            //发送json数据需要设置contentType
            s.setContentType("application/json");
            post.setEntity(s);
            response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回json格式：
                String result = EntityUtils.toString(response.getEntity());
                return JSON.parseObject(result, type);
            } else {
                log.error("拉取失败,错误编码为：" + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get请求，参数拼接在地址上
     *
     * @param url 请求地址加参数
     * @return 响应
     */
    public static <T> T doGet(String url, Class<T> cl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                return JSON.parseObject(result, new TypeReference<T>() {
                });
            } else {
                log.error("拉取失败,错误编码为：" + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *   post 请求  请求头携带参数 （非json格式）
     * @param url
     * @param param
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Map<String,String> param, TypeReference<T> type) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem =  iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            post.setEntity(entity);
            response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回json格式：
                String result = EntityUtils.toString(response.getEntity());
                return JSON.parseObject(result, type);
            } else {
                log.error("拉取失败,错误编码为：" + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
