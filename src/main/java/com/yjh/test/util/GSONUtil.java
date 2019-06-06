package com.yjh.test.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSONUtil {
    private static Gson instance = new GsonBuilder().serializeNulls().create();
    public static String toJson(Object o) {
        return instance.toJson(o);
    }
}
