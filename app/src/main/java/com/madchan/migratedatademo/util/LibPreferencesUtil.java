package com.madchan.migratedatademo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * SharedPreference工具类库
 */
public class LibPreferencesUtil {

    /** 调试模式 */
    public final static String DEBUG_MODE = "DEBUG_MODE";

    /** 保存默认设置 */
    public static void put(Context context, String key, boolean value) {
        put(context, null, key, value);
    }

    /** 保存默认设置 */
    public static void put(Context context, String name, String key, boolean value) {
        Editor editor = getEditor(context, name);
        editor.putBoolean(key, value);
        editor.apply();
    }

    /** 保存默认设置 */
    public static void put(Context context, String key, int value) {
        put(context, null, key, value);
    }

    /** 保存默认设置 */
    public static void put(Context context, String name, String key, int value) {
        Editor editor = getEditor(context, name);
        editor.putInt(key, value);
        editor.apply();
    }

    /** 保存默认设置 */
    public static void put(Context context, String key, long value) {
        put(context, null, key, value);
    }

    /** 保存默认设置 */
    public static void put(Context context, String name, String key, long value) {
        Editor editor = getEditor(context, name);
        editor.putLong(key, value);
        editor.apply();
    }

    /** 保存默认设置 */
    public static void put(Context context, String key, String value) {
        put(context, null, key, value);
    }

    /** 保存默认设置 */
    public static void put(Context context, String name, String key, String value) {
        Editor editor = getEditor(context, name);
        editor.putString(key, value);
        editor.apply();
    }

    /** 从默认设置获取值.默认false */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /** 从默认设置获取值 */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getDefaultPreference(context).getBoolean(key, defValue);
    }

    /** 从默认设置获取值.默认false */
    public static boolean getBoolean(Context context, String name, String key) {
        return getBoolean(context, name, key, false);
    }

    /** 从默认设置获取值 */
    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        return getSpecifiedPreference(context, name).getBoolean(key, defValue);
    }

    /** 从默认设置获取值，默认0 */
    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    /** 从默认设置获取值，默认0 */
    public static int getInt(Context context, String key, int defValue) {
        return getDefaultPreference(context).getInt(key, defValue);
    }

    /** 从默认设置获取值，默认0 */
    public static int getInt(Context context, String name, String key) {
        return getInt(context, name, key, 0);
    }

    /** 从默认设置获取值，默认0 */
    public static int getInt(Context context, String name, String key, int defValue) {
        return getSpecifiedPreference(context, name).getInt(key, defValue);
    }

    /** 从默认设置获取值，默认0 */
    public static long getLong(Context context, String key) {
        return getLong(context, key, 0L);
    }

    /** 从默认设置获取值，默认0 */
    public static long getLong(Context context, String key, long defValue) {
        return getDefaultPreference(context).getLong(key, defValue);
    }

    /** 从默认设置获取值，默认0 */
    public static long getLong(Context context, String name, String key) {
        return getLong(context, name, key, 0L);
    }

    /** 从默认设置获取值，默认0 */
    public static long getLong(Context context, String name, String key, long defValue) {
        return getSpecifiedPreference(context, name).getLong(key, defValue);
    }

    /** 从默认设置获取值，默认"" */
    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    /** 从默认设置获取值 */
    public static String getString(Context context, String key, String defValue) {
        return getDefaultPreference(context).getString(key, defValue);
    }

//    /** 从默认设置获取值，默认"" */
//    public static String getString(Context context, String name, String key) {
//        return getString(context, name, key, "");
//    }

    /** 从默认设置获取值 */
    public static String getString(Context context, String name, String key, String defValue) {
        return getSpecifiedPreference(context, name).getString(key, defValue);
    }

    /** 获取Editor */
    public static Editor getEditor(Context context, String name) {
        return TextUtils.isEmpty(name) ? getDefaultPreference(context).edit() : getSpecifiedPreference(context, name).edit();
    }

    /** 获取默认Preference */
    public static SharedPreferences getDefaultPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** 获取指定的Preference */
    public static SharedPreferences getSpecifiedPreference(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /** 移除指定键的值 */
    public static void remove(Context context, String key) {
        remove(context, null, key);
    }

    /** 移除指定键的值 */
    public static void remove(Context context, String name, String key) {
        Editor editor = getEditor(context, name);
        editor.remove(key);
        editor.apply();
    }

    /** 清空所有 */
    public static void clear(Context context, String name) {
        Editor editor = getEditor(context, name);
        editor.clear();
        editor.apply();
    }

}
