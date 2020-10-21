package com.madchan.migratedatademo.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * JSON工具类
 * @author chenf
 */
public class JSONUtil {

    // 解决Gson解析时候特殊符号，被转义的问题，如’单引号
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 实体类转为JSON字符串
     * 注：实体类必须支持序列化
     * @param obj   实体类
     * @return
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * JSON字符串转为实体类
     * @param json  JSON字符串
     * @param t     实体类类型
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> t) {
        if(TextUtils.isEmpty(json))
            return null;
        try {
            return gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            return  null;
        }
    }

    /**
     * JSON字符串转为实体类
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type t) {
        try {
            return gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * JSON数组转为List类型
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonArray(String json, Class<T> cls) {
        Type type = new ParameterizedTypeImpl(cls);
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return  null;
        }
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
