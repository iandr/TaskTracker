package com.geekbrains.gwt.client.utils;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import org.fusesource.restygwt.client.Method;

import java.util.HashMap;
import java.util.Map;

public class ErrorUtil {

    private void ErrorUtil() {};

    public static Map getError(Method method) {
        Map<String, String> map = new HashMap<String, String>();
        JSONValue parsed  = JSONParser.parseStrict(method.getResponse().getText());
        JSONObject jsonObject = parsed .isObject();
        if (jsonObject != null) {
            for (String key : jsonObject.keySet()) {
                JSONValue jsonValue = jsonObject.get(key);
                JSONString jsonString = jsonValue.isString();
                String stringValue = (jsonString == null) ? jsonValue.toString() : jsonString.stringValue();
                map.put(key, stringValue);
            }
        }
        return map;
    }
}
