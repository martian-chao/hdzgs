package com.cars.util.http;

import com.cars.util.log.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**http工具类
 * Created by liuyanchao
 * on 2018/8/17.
 */
public class HttpUtil {
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
}
