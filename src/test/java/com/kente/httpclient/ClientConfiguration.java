package com.kente.httpclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class ClientConfiguration {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
            @Override
            public HttpMessageParser<HttpResponse> create(
                SessionInputBuffer buffer, MessageConstraints constraints) {
                LineParser lineParser = new BasicLineParser() {
                    @Override
                    public Header parseHeader(final CharArrayBuffer buffer) {
                        return super.parseHeader(buffer);
                    }
                };
                
                return new DefaultHttpResponseParser(
                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
                    @Override
                    protected boolean reject(final CharArrayBuffer line, int count) {
                        return false;
                    }
                };
            }
        };
        
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
        
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
            requestWriterFactory, responseParserFactory);
        
        SSLContext sslContext = SSLContexts.createSystemDefault();
        
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslContext))
            .build();
        
        DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[] {InetAddress.getByAddress(new byte[] {127,0,0,1})};
                } else {
                    return super.resolve(host);
                }
            }
        };
        
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
            socketFactoryRegistry, connFactory, dnsResolver);
        
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
        connManager.setValidateAfterInactivity(1000);
        
        MessageConstraints messageConstraints = MessageConstraints.custom()
            .setMaxHeaderCount(200)
            .setMaxLineLength(2000)
            .build();
        
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8)
            .setMessageConstraints(messageConstraints)
            .build();
        
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);
        
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);
        connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);
        
        CookieStore cookieStore = new BasicCookieStore();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.DEFAULT)
            .setExpectContinueEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
            .build();
        
        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connManager)
            .setDefaultCookieStore(cookieStore)
            .setDefaultCredentialsProvider(credentialsProvider)
//            .setProxy(new HttpHost("myproxy", 8080))
            .build();
        
        HttpGet httpGet = new HttpGet("http://httpbin.org/get");
        
//        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
//            .setSocketTimeout(5000)
//            .setConnectTimeout(5000)
//            .setConnectionRequestTimeout(5000)
//            .setProxy(new HttpHost("myotherproxy", 8080))
//            .build();
//        httpGet.setConfig(requestConfig);
        
        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
        httpClientContext.setCredentialsProvider(credentialsProvider);
        
        System.out.println("Executing request " + httpGet.getURI());
        CloseableHttpResponse response = httpClient.execute(httpGet, httpClientContext);
        
        System.out.println("--------------------------------------------------");
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
        System.out.println("--------------------------------------------------");
        
        httpClientContext.getRequest();
        httpClientContext.getHttpRoute();
        httpClientContext.getTargetAuthState();
        httpClientContext.getTargetAuthState();
        httpClientContext.getCookieOrigin();
        httpClientContext.getCookieSpec();
        httpClientContext.getUserToken();
        
        response.close();
        httpClient.close();
    }
}
