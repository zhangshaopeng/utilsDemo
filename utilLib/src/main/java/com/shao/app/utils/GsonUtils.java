package com.shao.app.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:关于Gson解析的一些工具类  需要依赖（Gson）
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class GsonUtils {

    private static Gson gson = new Gson();

    /**
     * 通过Gson获取Object
     *
     * @param json json字符串
     * @param cls
     * @return 可能为null的Object
     */
    public static <T> T getObject(String json, Class<T> cls) {
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 通过Gson获取class集合
     *
     * @param json    json字符串
     * @param typeOfT 例如 new TypeToken<List<T>>() {}.getType()
     * @return 返回一个不为null的list集合，只需要做size是否为0的判断即可
     */
    public static <T> List<T> getList(String json, Type typeOfT) {

        try {
            List<T> list = gson.fromJson(json, typeOfT);
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * 通过Gson获取String 集合
     *
     * @param json json字符串
     * @return 返回一个不为null的StrList集合，只需要做size是否为0的判断即可
     */
    public static List<String> getStrList(String json) {
        return getList(json, new TypeToken<List<String>>() {
        }.getType());
    }

    /**
     * 通过Gson获取map的list集合
     *
     * @param json json字符串
     * @return 返回一个不为null的list集合，只需要做size是否为0的判断即可
     */
    public static List<Map<String, Object>> listKeyMap(String json) {
        return getList(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }


    /**
     * 通过Gson获取map集合
     *
     * @param json json字符串
     * @return 返回一个不为null的map集合
     */
    public static Map<String, Object> getMap(String json) {

        try {
            Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            if (map != null) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }


    /**
     * 序列化生成json
     *
     * @param object 可以是 class ，list. map等
     * @return 序列化后的json
     */
    public static String getJsonByObj(Object object) {
        return gson.toJson(object);
    }
}
