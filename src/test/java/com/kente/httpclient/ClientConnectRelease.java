package com.kente.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ClientConnectRelease {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpGet httpGet = new HttpGet("http://httpbin.org/get");
        System.out.println("Executing request " + httpGet.getRequestLine());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("------------------------------------------------");
        System.out.println(response.getStatusLine());
        
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inStream = entity.getContent();
            inStream.read();
        } else {

        }
        
        response.close();
        httpClient.close();
    }
}
