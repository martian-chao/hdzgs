package com.cars.util.http;

import com.cars.util.log.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.UnknownHostException;

/**http工具类
 * Created by liuyanchao
 * on 2018/8/17.
 */
public class HttpUtil {
    /**
     *
     */
    public static HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 5) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };
    /**
     * 发送http的get请求
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        InputStream is = null;
        BufferedReader br = null;
        String ret = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            // 设置超时时间
            // setConnectTimeout：设置连接超时时间，单位毫秒。
            // setConnectionRequestTimeout：设置从connect
            // Manager获取Connection超时时间，单位毫秒。
            // setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("connection", "Keep-Alive");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // 连接成功
            ret = getResponse(httpResponse);
            httpGet.releaseConnection();
        } catch (Exception e) {
            LogUtil.infoHttp(e.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                LogUtil.infoHttp(e.getMessage());
            }
        }
        return ret;
    }

    /**获取返回值
     *
     * @param httpResponse
     * @return
     * @throws Exception
     */
    private static String getResponse(HttpResponse httpResponse) throws Exception {

        if (200 == httpResponse.getStatusLine().getStatusCode()) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null)
                return EntityUtils.toString(httpEntity, "UTF-8");
        }
        return null;
    }

    /**
     * POST JSON 请求
     * @param url
     * @param jsonParam
     * @return
     */
    public static String postJson(String url, String jsonParam) {
        LogUtil.infoHttp("请求url：" + url + ";请求参数：" + jsonParam);
        String getJson = "";
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = null;
        try {

            httpclient = HttpClients.custom().setRetryHandler(HttpUtil.myRetryHandler).build();
            httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);

            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String str = "";
            while ((str = rd.readLine()) != null) {
                getJson += str;
            }
            LogUtil.infoHttp("返回值：" + getJson);

            response.close();
        } catch (Exception e) {
            LogUtil.infoHttp(e.getMessage());
        } finally {
            httpPost.abort();
            httpPost.releaseConnection();
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getJson;
    }
}
