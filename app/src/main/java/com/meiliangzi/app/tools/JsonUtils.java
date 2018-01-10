package com.meiliangzi.app.tools;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {
    private static Gson gson = new Gson();

    private JsonUtils() {
    }


    /**
     * 将JSON对象转换为一个Bean对象
     */
    public static <T> T parserJSONObject(JSONObject jsonObj, Class<T> resultCls) {
        return gson.fromJson(jsonObj.toString(), resultCls);
    }

    /**
     * 将jsonString对象转换为一个Bean对象
     */
    public static <T> T parserJSONObject(String jsonString, Class<T> resultCls) {
        return gson.fromJson(jsonString, resultCls);
    }

    /**
     * 将JSON数组转换为一个集合
     */
    public static <T> List<T> parserJSONArray(JSONObject jsonObj, Type type) throws JSONException {
        return gson.fromJson(jsonObj.toString(), type);
    }

    /**
     * 将jsonString数组转换为一个集合
     */
    public static <T> List<T> parserJSONArray(String jsonString, Type type) throws JSONException {
        return gson.fromJson(jsonString, type);
    }

    /**
     * 将jsonString数组转换为一个集合
     */
    public static String toJSONString(Object type) {
        return gson.toJson(type);
    }
}
