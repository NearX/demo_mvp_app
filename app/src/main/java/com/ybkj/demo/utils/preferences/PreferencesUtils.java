package com.ybkj.demo.utils.preferences;

import android.content.Context;
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


/**
 * @author neo.duan
 * @date 2018/1/8 10:25
 * @desc preferences操作工具类
 */
public class PreferencesUtils extends BasePreferences {
    // 文件名
    private static final String PREFERENCES_NAME = Constants.PROJECT;

    private PreferencesUtils() {
        throw new AssertionError();
    }

    /**
     * 保存数据
     *
     * @param key
     * @return boolean true:成功 false:失败
     */
    public static boolean putData(Context context, String key, Object value) {
        return saveData(context, PREFERENCES_NAME, key, value);
    }

    /**
     * 从文件中获取数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {
        if (context == null) {
            return defValue;
        }
        return getData(context, PREFERENCES_NAME, key, defValue);
    }

    /**
     * 移除指定KEY
     *
     * @param key
     */
    public static void removeKey(Context context, String key) {
        remove(context, PREFERENCES_NAME, key);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        clear(context, PREFERENCES_NAME);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        putData(context, key, value);
    }

    public static void putInt(Context context, String key, int value) {
        putData(context, key, value);
    }

    public static void putLong(Context context, String key, long value) {
        putData(context, key, value);
    }

    public static void putString(Context context, String key, String value) {
        putData(context, key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return (Boolean) getData(context, key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        return (Integer) getData(context, key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        return (Long) getData(context, key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return (String) getData(context, key, defValue);
    }

    /**
     * 保存对象，object所在的类必须实现{@link Serializable}接口
     *
     * @param key    存储的关键词
     * @param object 传入的json对象
     */
    public static void saveObject(String key, Object object) {
        if (object == null) {
            removeKey(SampleApplicationLike.getInstance(), key);
            return;
        }

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
            putString(SampleApplicationLike.getInstance(), key, strBase64);
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
    public static Object getObject(String key, Object defValue) {
        Object object = null;
        String strBase64 = getString(SampleApplicationLike.getInstance(), key, "");
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

}
