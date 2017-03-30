package com.kente.httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ClientAbortMethod {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpGet httpGet = new HttpGet("http://httpbin.org/get");
        
        System.out.println("Executing request " + httpGet.getURI());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        httpGet.abort();
        response.close();
        httpClient.close();
    }
}
