package com.kpi.diploma.smartroads.service.util.http;

public interface HttpClient {

    HttpRequest createGetRequest(String url);

    HttpRequest createPostRequest(String url);

    HttpRequest createDeleteRequest(String url);

    HttpRequest createPutRequest(String url);
}
