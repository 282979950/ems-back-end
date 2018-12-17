package com.tdmh.common;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Json数据封装
 *
 * @author litairan on 2018/6/29.
 */
@Getter
@Setter
public class JsonData{

    /**
     * 消息状态(true:成功,false:失败)
     */
    private int status;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public JsonData(int status) {
        this.status = status;
    }

    public static JsonData success(Object object, String msg) {
        JsonData jsonData = new JsonData(0);
        jsonData.data = object;
        jsonData.message = msg;
        return jsonData;
    }

    public static JsonData successData(Object object) {
        JsonData jsonData = new JsonData(0);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData successMsg(String msg) {
        JsonData jsonData = new JsonData(0);
        jsonData.message = msg;
        return jsonData;
    }

    public static JsonData success() {
        return new JsonData(0);
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(1);
        jsonData.message = msg;
        return jsonData;
    }

    public static JsonData fail(int status, String msg) {
        JsonData jsonData = new JsonData(status);
        jsonData.message = msg;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", status);
        if (message != null) {
            result.put("message", message);
        }
        if (data != null) {
            result.put("data", data);
        }
        return result;
    }
}
