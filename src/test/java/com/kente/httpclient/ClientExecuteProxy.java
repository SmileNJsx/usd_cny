package com.kente.httpclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientExecuteProxy {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpHost target = new HttpHost("httpbin.org", 443, "https");
        HttpHost proxy = new HttpHost("127.0.0.1", 8081, "http");
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        HttpGet request = new HttpGet("/");
        request.setConfig(requestConfig);
        
        System.out.println("Executing request " + request.getRequestLine() + "to" + target + "via" + proxy);
        
        CloseableHttpResponse httpResponse = httpClient.execute(target, request);
        
        System.out.println("----------------------------------------------");
        System.out.println(httpResponse.getStatusLine());
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
        httpResponse.close();
        httpClient.close();
    }
}
