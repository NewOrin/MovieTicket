package com.etc.movieticket.utils;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.entity.Info;
import com.etc.movieticket.entity.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class MyJsonUtils {

    public static Object json2List(String json) {
        return JSON.parseArray(json, Movie.class);
    }

}
