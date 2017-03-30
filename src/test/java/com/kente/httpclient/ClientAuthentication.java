package com.kente.httpclient;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientAuthentication {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
            new AuthScope("httpbin.org", 80),
            new UsernamePasswordCredentials("user", "passwd"));
        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credentialsProvider)
            .build();
        
        HttpGet httpGet = new HttpGet("http://httpbin.org/basic-auth/user/passwd");
        
        System.out.println("Executing request " + httpGet.getURI());
        
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        
        System.out.println("------------------------------------------------------");
        System.out.println(httpResponse.getStatusLine());
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
        
        httpResponse.close();
        httpClient.close();
        
    }
}