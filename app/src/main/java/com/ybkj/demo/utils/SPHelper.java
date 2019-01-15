/*
 * Copyright (c) 2015. The DAHUA LECHANGE Robot X Project.
 */

package com.ybkj.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.ybkj.demo.SampleApplicationLike;
import com.ybkj.demo.common.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 配置信息存储帮助类
 */
public class SPHelper {
    private final static String PRE_NAME = Constants.PROJECT;

    private SharedPreferences mConfig = null;

    private SPHelper() {
    }

    public static SPHelper getInstance() {
        return Instance.ph;
    }

    /**
     * 若不存在Key，则返回 空字符串
     */
    public String getString(String key) {
        return getConfig().getString(key, "");
    }

    /**
     * 若不存在Key，则返回 空字符串
     */
    public Set<String> getStringSet(String key) {
        return getConfig().getStringSet(key, new HashSet<String>());
    }

    /**
     * 若不存在Key，则返回0
     */
    public int getInt(String key) {
        return getConfig().getInt(key, 0);
    }

    /**
     * 若不存在Key，则返回0
     */
    public float getFloat(String key) {
        return getConfig().getFloat(key, 0f);
    }

    /**
     * 若不存在Key，则返回 false
     */
    public boolean getBoolean(String key) {
        return getConfig().getBoolean(key, false);
    }

    /**
     * 若不存在Key，则返回 0
     */
    public long getLong(String Key) {
        return getConfig().getLong(Key, 0);
    }

    public void set(String key, String value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void set(String key, int value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void set(String key, float value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void set(String key, boolean value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void set(String key, long value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void set(String key, Set<String> value) {
        SharedPreferences.Editor editor = getConfig().edit();
        editor.clear();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * 是否存在key值
     */
    public boolean contains(String key) {
        return getConfig().contains(key);
    }

    private SharedPreferences getConfig() {
        if (mConfig == null) {
            mConfig = SampleApplicationLike.getInstance().getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        }
        return mConfig;
    }

    /**
     * 清楚所有数据
     */
    public void clearAppSharedPreferences() {
        getConfig().edit().clear().apply();
    }

    /**
     * 清楚指定数据
     */
    public void clearSharedPreferencesByKey(String key) {
        SharedPreferences.Editor edit = getConfig().edit();
        edit.remove(key);
        edit.apply();
    }

    /**
     * 保存对象，object所在的类必须实现{@link Serializable}接口
     *
     * @param key    存储的关键词
     * @param object 传入的json对象
     */
    public void saveObject(String key, Object object) {
        if (object == null) clearSharedPreferencesByKey(key);

        if (!(object instanceof Serializable)) {
            throw new RuntimeException("请将object所在的类实现Serializable接口");
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String strBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            set(key, strBase64);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (oos != null) {//检查资源是否为null
                try {
                    oos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取存储的object
     *
     * @param key      取出的关键词
     * @param defValue 传入的json对象
     * @return 获取成功则返回存储的object，否则返回默认值
     */
    public Object getObject(String key, Object defValue) {
        Object object = null;
        String strBase64 = getString(key);
        if (TextUtils.isEmpty(strBase64)) {
            return defValue;
        }
        byte[] base64 = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = null;
        ObjectInputStream bis = null;
        try {
            bais = new ByteArrayInputStream(base64);
            bis = new ObjectInputStream(bais);
            object = bis.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (bis != null) {//检查资源是否为null
                try {
                    bis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return defValue;
    }

    private static class Instance {
        private static SPHelper ph = new SPHelper();
    }
}
