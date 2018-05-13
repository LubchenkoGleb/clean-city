package com.kpi.diploma.smartroads.service.util.http;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.util.exception.HttpException;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@Slf4j
public class HttpRequest {

    private static final String JSON_VALUE = "Content-Type: application/json";
    private HttpClient client;
    private HttpRequestBase httpRequestBase;
    private URIBuilder uriBuilder;
    private ObjectMapper mapper;

    public HttpRequest(HttpRequestBase httpRequestBase, String url) {

        try {
            this.uriBuilder = new URIBuilder(url);
            this.httpRequestBase = httpRequestBase;
            this.httpRequestBase.setURI(uriBuilder.build());

        } catch (URISyntaxException e) {
            throw new HttpException(e.getMessage());
        }

        this.client = HttpClientBuilder.create().build();
        this.mapper = new ObjectMapper();
    }

    public HttpRequest addHeader(String title, String value) {
        log.info("'addHeader' param'{}, {}'", title, value);
        httpRequestBase.removeHeaders(title);
        httpRequestBase.addHeader(title, value);

        return this;
    }

    public HttpRequest addRequestParam(String title, String value) {
        log.info("'addRequestParam' params'{}, {}", title, value);

        uriBuilder.addParameter(title, value);

        try {
            httpRequestBase.setURI(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new HttpException(e.getMessage());
        }

        return this;
    }

    public HttpRequest setBody(JsonNode body) {
        log.info("'setBody' params'{}'", body);

        httpRequestBase.addHeader("content-type", "application/json");

        try {

            StringEntity requestEntity = new StringEntity(body.toString());
            ((HttpEntityEnclosingRequestBase) httpRequestBase).setEntity(requestEntity);
            return this;

        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e.getMessage());
        }
    }

    public HttpRequest setBody(Object body) {

        String jsonString = ConversionService.convertToJsonString(body);
        log.info("'setBody' params'{}'", jsonString);

        httpRequestBase.addHeader("content-type", "application/json");

        try {


            StringEntity requestEntity = new StringEntity(jsonString);
            ((HttpEntityEnclosingRequestBase) httpRequestBase).setEntity(requestEntity);
            return this;

        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }

    public HttpRequest setBody(String body) {
        log.info("'setBody' params'{}'", body);

        try {

            StringEntity requestEntity = new StringEntity(body);
            ((HttpEntityEnclosingRequestBase) httpRequestBase).setEntity(requestEntity);
            return this;

        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e.getMessage());
        }
    }

    public HttpResponse sendWithCipHttpResponse() {
        log.info("'sendWithCipHttpResponse' invoked for'{}'", httpRequestBase);

        org.apache.http.HttpResponse execute = null;
        try {
            execute = client.execute(httpRequestBase);
            HttpEntity entity = execute.getEntity();

            if (entity.getContentLength() != 0 && !entity.getContentType().toString().startsWith(JSON_VALUE)) {

                String message = "IncorrectContentTypeException. Content-Type - " + execute.getEntity().getContentType();
                log.error(message);
                throw new HttpException(message);

            }

            InputStream responseBodyInputStream = execute.getEntity().getContent();
            JsonNode body = mapper.readTree(responseBodyInputStream);
            HttpResponse response = new HttpResponse(body, execute.getStatusLine().getStatusCode());
            log.info("response body - '" + body + "', status - " + execute.getStatusLine());

            return response;

        } catch (JsonMappingException e) {
            return new HttpResponse(mapper.createObjectNode(), execute.getStatusLine().getStatusCode());
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }

    public org.apache.http.HttpResponse sendWithApacheHttpResponse() {
        log.info("send request with Apache Http Response");

        try {

            org.apache.http.HttpResponse execute = client.execute(httpRequestBase);
            log.info("response status'{}'", execute.getStatusLine());
            return execute;

        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }
}
