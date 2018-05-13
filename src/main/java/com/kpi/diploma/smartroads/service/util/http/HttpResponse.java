package com.kpi.diploma.smartroads.service.util.http;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class HttpResponse {

    private JsonNode body;
    private int status;

    public HttpResponse(JsonNode body, Integer status) {
        this.body = body;
        this.status = status;
    }
}
