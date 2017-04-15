package com.kente.main;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.kente.usdcny.DownLoadUsdCnyPage;

public class Start {
    public static void main(String[] args) {
        while(true) {
            try {
                DownLoadUsdCnyPage.DownLoadToFile();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
