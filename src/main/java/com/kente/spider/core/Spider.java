package com.kente.spider.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Spider {
    public static String getPageContent(String start_date,String end_date, String pageParam) throws ClientProtocolException, IOException {
        String url = "http://srh.bankofchina.com/search/whpj/search.jsp";
        Map<String, String> params = new HashMap<String, String>();
        params.put("erectDate", start_date);
        params.put("nothing", end_date);
        params.put("pjname", "1316");
        params.put("page", pageParam);
        
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = postForm(url, params);
        HttpResponse httpResponse = sendRequest(httpClient, httpPost);
        return parseResponse(httpResponse);
    }
    
    
    private static String parseResponse(HttpResponse response) throws ParseException, IOException {
        HttpEntity entity = response.getEntity();
        String body = null;
        body = EntityUtils.toString(entity);
        return body;
    }
    
    private static HttpPost postForm(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        for(String key : params.keySet()) {
            values.add(new BasicNameValuePair(key, params.get(key)));
        }
        
        httpPost.setEntity(new UrlEncodedFormEntity(values, "UTF-8"));
        
        return httpPost;
    }
    
    private static HttpResponse sendRequest(HttpClient httpClient, HttpUriRequest request) throws ClientProtocolException, IOException {
        HttpResponse response = null;
        response = httpClient.execute(request);
        return response;
    }
}
