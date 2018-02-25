package com.kpi.diploma.smartroads.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.*;

@Slf4j
public class ConversionService {

    private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static String convertToJsonString(Object obj) {
        return mapper.valueToTree(obj).toString();
    }

    public static JsonNode convertToJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    public static ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return mapper.createArrayNode();
    }

    public static <T> T convertToObject(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(JsonNode node, Class<T> clazz) {
        Object object = null;
        try {
            object = mapper.treeToValue(node, clazz);
            return clazz.cast(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertTimeToString(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Formatter formatter = new Formatter();
        return formatter.format("%tB %td %tY", cal, cal, cal).toString();
    }

    public static Map<String, String> jsonNodeToMap(JsonNode node) {
        Map<String, String> jsonData = new HashMap<>();
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue.isObject()) {
                System.out.println(fieldName + " :");
                jsonData = jsonNodeToMap(fieldValue);
            } else {
                String value = fieldValue.asText();
                jsonData.put(fieldName, value);
            }
        }
        return jsonData;
    }

    public static Double roundDouble(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.valueOf(decimalFormat.format(number));
    }
}
