package com.kpi.diploma.smartroads.service.util.http.impl;

import com.kpi.diploma.smartroads.service.util.http.HttpClient;
import com.kpi.diploma.smartroads.service.util.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpClientImpl implements HttpClient {

    @Override
    public HttpRequest createGetRequest(String url) {
        log.info("'createGetRequest' params'{}'", url);

        HttpGet httpRequestBase = new HttpGet();
        return new HttpRequest(httpRequestBase, url);

    }

    @Override
    public HttpRequest createPostRequest(String url) {
        log.info("'createPostRequest' params'{}'", url);

        HttpPost httpRequestBase = new HttpPost();
        return new HttpRequest(httpRequestBase, url);

    }

    @Override
    public HttpRequest createDeleteRequest(String url) {
        log.info("'createDeleteRequest' params'{}'", url);

        HttpDelete httpRequestBase = new HttpDelete();
        return new HttpRequest(httpRequestBase, url);

    }

    @Override
    public HttpRequest createPutRequest(String url) {
        log.info("'createPutRequest' params'{}'", url);

        HttpPut httpRequestBase = new HttpPut();
        return new HttpRequest(httpRequestBase, url);

    }
}
