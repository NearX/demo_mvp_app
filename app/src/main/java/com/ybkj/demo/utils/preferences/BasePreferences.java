package com.ybkj.demo.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

/**
 * @author : neo.duan
 * @date : 	 2016/7/25
 * @desc : preferences操作基类
 */
public class BasePreferences {

    /**
     * 保存数据
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static boolean saveData(Context context, String fileName, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            if (value == null) {
                editor.putString(key, "");
            } else {
                editor.putString(key, String.valueOf(value));
            }
        }
        return editor.commit();
    }

    /**
     * 取值
     *
     * @param context
     * @param fileName
     * @param key
     * @param defValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getData(Context context, String fileName, String key, Object defValue) {
        if (context == null || fileName == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Set<?>) {
            return sp.getStringSet(key, (Set<String>) defValue);
        } else {
            if (defValue == null) {
                return sp.getString(key, "");
            }
            return sp.getString(key, String.valueOf(defValue));
        }
    }

    /**
     * 清空
     *
     * @param fileName
     */
    public static void clear(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 移除指定的KEY
     *
     * @param fileName
     * @param key
     */
    public static void remove(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

}
