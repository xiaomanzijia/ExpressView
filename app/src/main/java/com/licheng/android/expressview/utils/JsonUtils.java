package com.licheng.android.expressview.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by licheng on 5/1/17.
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    /**
     * 对象转json
     *
     * @param obj
     *            需要转换json的对象
     * @param cls
     *            对象类型
     * @return 把对象转换成json的string
     */
    public static String objectToString(Object obj, Class cls) {
        String json = gson.toJson(obj, cls);
        return json;
    }

    /**
     * json转对象
     *
     * @param json
     *            需要转换的json
     * @return json解析之后的对象
     */
    public static Object StringToObject(String json, Class cls) {
        Object obj = gson.fromJson(json, cls);
        return obj;
    }

    /**
     * 集合转json
     *
     * @param <T>
     *            对象类型
     * @param list
     *            需要转换json的对象集合
     * @return 转换成json之后的string
     */
    public static <T> String objectArrayToString(List<T> list) {
        String json = gson.toJson(list, new TypeToken<List<T>>() {
        }.getType());
        return json;
    }


    /**
     * json转集合
     *
     * @param <T>
     *
     * @param json
     *            需要转换成对象集合的json
     *            <T> 对象类型
     * @return 解析json之后的对象集合
     */
    public static <T> List<T> StringToObjectArray(String json, Class<T> cls) {
        List<T> list = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }
}
