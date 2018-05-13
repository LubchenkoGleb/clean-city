package com.kpi.diploma.smartroads.service.util.http.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.service.util.http.HttpClient;
import com.kpi.diploma.smartroads.service.util.http.HttpMethods;
import com.kpi.diploma.smartroads.service.util.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class HttpMethodsImpl implements HttpMethods {

    private final HttpClient httpClient;

    @Autowired
    public HttpMethodsImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public JsonNode sendGetRequest(String url) {
        return httpClient
                .createGetRequest(url)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendGetRequest(String url, Map<String, String> headers, Map<String, String> params) {

        HttpRequest HttpRequest = httpClient.createGetRequest(url);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));
        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendGetRequestWithParams(String url, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createGetRequest(url);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendGetRequestWithHeaders(String url, Map<String, String> headers) {
        HttpRequest HttpRequest = httpClient.createGetRequest(url);

        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendGetRequestWithParam(String url, String paramTitle, String paramValue) {
        return httpClient
                .createGetRequest(url)
                .addRequestParam(paramTitle, paramValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendGetRequestWithHeader(String url, String headerTitle, String headerValue) {
        return httpClient
                .createGetRequest(url)
                .addHeader(headerTitle, headerValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequest(String url) {
        return httpClient
                .createPostRequest(url)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequest(String url, JsonNode body) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequest(String url, Object body) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createPostRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));
        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithParamsAndBody(String url, JsonNode body, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createPostRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithParams(String url, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createPostRequest(url);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithHeaders(String url, JsonNode body, Map<String, String> headers) {
        HttpRequest HttpRequest = httpClient.createPostRequest(url)
                .setBody(body);

        headers.entrySet().forEach(h -> HttpRequest.addRequestParam(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .addRequestParam(paramTitle, paramValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithHeaderAndBody(String url, JsonNode body, String headerTitle, String headerValue) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .addHeader(headerTitle, headerValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPostRequestWithHeader(String url, String headerTitle, String headerValue) {
        return httpClient
                .createPostRequest(url)
                .addHeader(headerTitle, headerValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequest(String url) {
        return httpClient
                .createDeleteRequest(url)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequest(String url, JsonNode body) {
        return httpClient
                .createPutRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequest(String url, Object body) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createPutRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));
        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequestWithParams(String url, JsonNode body, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createPutRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequestWithHeaders(String url, JsonNode body, Map<String, String> headers) {
        HttpRequest HttpRequest = httpClient.createPutRequest(url)
                .setBody(body);

        headers.entrySet().forEach(h -> HttpRequest.addRequestParam(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue) {
        return httpClient
                .createPutRequest(url)
                .setBody(body)
                .addRequestParam(paramTitle, paramValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendPutRequestWithHeader(String url, JsonNode body, String headerTitle, String headerValue) {
        return httpClient
                .createPutRequest(url)
                .setBody(body)
                .addHeader(headerTitle, headerValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequest(String url) {
        return httpClient
                .createDeleteRequest(url)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequest(String url, JsonNode body) {
        return httpClient
                .createDeleteRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequest(String url, Object body) {
        return httpClient
                .createPostRequest(url)
                .setBody(body)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createDeleteRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));
        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequestWithParams(String url, JsonNode body, Map<String, String> params) {
        HttpRequest HttpRequest = httpClient.createDeleteRequest(url)
                .setBody(body);

        params.entrySet().forEach(p -> HttpRequest.addRequestParam(p.getKey(), p.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequestWithHeaders(String url, JsonNode body, Map<String, String> headers) {
        HttpRequest HttpRequest = httpClient.createDeleteRequest(url)
                .setBody(body);

        headers.entrySet().forEach(h -> HttpRequest.addHeader(h.getKey(), h.getValue()));

        return HttpRequest
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequestWithParam(String url, String paramTitle, String paramValue) {
        return httpClient
                .createDeleteRequest(url)
                .addRequestParam(paramTitle, paramValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue) {
        return httpClient
                .createDeleteRequest(url)
                .setBody(body)
                .addRequestParam(paramTitle, paramValue)
                .sendWithCipHttpResponse()
                .getBody();
    }

    @Override
    public JsonNode sendDeleteRequestWithHeader(String url, JsonNode body, String headerTitle, String headerValue) {
        return httpClient
                .createDeleteRequest(url)
                .setBody(body)
                .addHeader(headerTitle, headerValue)
                .sendWithCipHttpResponse()
                .getBody();
    }
}
