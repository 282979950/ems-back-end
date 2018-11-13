package com.tdmh.utils.map;



import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tdmh.exception.ParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author Administrator on 2018/11/12.
 */
public class BaiDuMap {
    private final static String AK = "8120KhYpyTh0X4kWFh9DbYSgVGPeI0Y5";

    // 调用百度地图API根据地址，获取坐标
    public static Map<String,String> getCoordinate(String address) {
        Map<String,String> bdMap = Maps.newHashMap();
        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.parseObject(json);
                if ("0".equals(obj.getString("status"))) {
                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
                    DecimalFormat df = new DecimalFormat("#.######");
                    bdMap.put("lng",df.format(lng));
                    bdMap.put("lat",df.format(lat));
                    return bdMap;
                }else if ("2".equals(obj.getString("status"))){
                    throw new ParameterException("地址名有误");
                }
            }
        }
        return null;
    }

    //百度坐标转换成腾讯坐标
    public static Map<String,String> geoConveter(Map<String,String> bdMap) {
        Map<String,String> txMap = Maps.newHashMap();
        if (bdMap != null && bdMap.size() != 0) {
            String url = "http://api.map.baidu.com/geoconv/v1/?coords="+bdMap.get("lng")+","+bdMap.get("lat")+"&from=5&to=3&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.parseObject(json);
                if ("0".equals(obj.getString("status"))) {
                    double lng = obj.getJSONArray("result").getJSONObject(0).getDouble("x"); // 经度
                    double lat = obj.getJSONArray("result").getJSONObject(0).getDouble("y"); // 纬度
                    DecimalFormat df = new DecimalFormat("#.######");
                    txMap.put("lng",df.format(lng));
                    txMap.put("lat",df.format(lat));
                    return txMap;
                }
            }
        }
        return null;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

}
