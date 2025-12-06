package com.example.dsl.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class HttpClient {
    private static final CloseableHttpClient client = HttpClients.createDefault();

    public static String get(String url) {
        try {
            HttpGet request = new HttpGet(url);
            return client.execute(request, response -> {
                return EntityUtils.toString(response.getEntity());
            });
        } catch (Exception e) {
            throw new RuntimeException("HTTP GET failed: " + url, e);
        }
    }
}
