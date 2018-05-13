package com.kpi.diploma.smartroads.service.util.http;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface HttpMethods {

    JsonNode sendGetRequest(String url);

    JsonNode sendGetRequest(String url, Map<String, String> headers, Map<String, String> params);

    JsonNode sendGetRequestWithParams(String url, Map<String, String> params);

    JsonNode sendGetRequestWithHeaders(String url, Map<String, String> headers);

    JsonNode sendGetRequestWithParam(String url, String paramTitle, String paramValue);

    JsonNode sendGetRequestWithHeader(String url, String headerTitle, String headerValue);

    JsonNode sendPostRequest(String url, JsonNode body);

    JsonNode sendPostRequest(String url, Object body);

    JsonNode sendPostRequest(String url);

    JsonNode sendPostRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params);

    JsonNode sendPostRequestWithParamsAndBody(String url, JsonNode body, Map<String, String> params);

    JsonNode sendPostRequestWithParams(String url, Map<String, String> params);

    JsonNode sendPostRequestWithHeaders(String url, JsonNode body, Map<String, String> headers);

    JsonNode sendPostRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue);

    JsonNode sendPostRequestWithHeaderAndBody(String url, JsonNode body, String headerTitle, String headerValue);

    JsonNode sendPostRequestWithHeader(String url, String headerTitle, String headerValue);

    JsonNode sendPutRequest(String url, JsonNode body);

    JsonNode sendPutRequest(String url, Object body);

    JsonNode sendPutRequest(String url);

    JsonNode sendPutRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params);

    JsonNode sendPutRequestWithParams(String url, JsonNode body, Map<String, String> params);

    JsonNode sendPutRequestWithHeaders(String url, JsonNode body, Map<String, String> headers);

    JsonNode sendPutRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue);

    JsonNode sendPutRequestWithHeader(String url, JsonNode body, String headerTitle, String headerValue);

    JsonNode sendDeleteRequest(String url, JsonNode body);

    JsonNode sendDeleteRequest(String url, Object body);

    JsonNode sendDeleteRequest(String url);

    JsonNode sendDeleteRequest(String url, JsonNode body, Map<String, String> headers, Map<String, String> params);

    JsonNode sendDeleteRequestWithParams(String url, JsonNode body, Map<String, String> params);

    JsonNode sendDeleteRequestWithHeaders(String url, JsonNode body, Map<String, String> headers);

    JsonNode sendDeleteRequestWithParam(String url, String paramTitle, String paramValue);

    JsonNode sendDeleteRequestWithParam(String url, JsonNode body, String paramTitle, String paramValue);

    JsonNode sendDeleteRequestWithHeader(String url, JsonNode body, String headerTitle, String headerValue);

}
