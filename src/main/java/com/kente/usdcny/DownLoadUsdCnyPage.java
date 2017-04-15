package com.kente.usdcny;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.kente.file.CreateFile;
import com.kente.file.WriteToFile;
import com.kente.spider.core.Spider;

public class DownLoadUsdCnyPage {
    
    private static int pageParam = 1;
    
    public static void DownLoadToFile() throws ClientProtocolException, IOException {
        CreateFile.createFileByOS();
        String content = Spider.getPageContent("2001-01-01", "2017-04-13", String.valueOf(pageParam));
        WriteToFile.writeByFilePath("D:/kente/usd_cny/" + String.valueOf(pageParam) + ".txt", content);
        pageParam++;
    }
}
