package com.etc.movieticket.utils;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.entity.Info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class JsonToString {
    /**
     * 封装成json字符串
     *
     * @param info
     * @param data
     * @return
     */
    public static String json2String(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        return JSON.toJSONString(map);
    }
}
