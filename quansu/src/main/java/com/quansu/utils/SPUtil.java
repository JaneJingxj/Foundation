

package com.quansu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.quansu.BaseApp;
import com.quansu.cons.Constants;
import com.ysnows.quansu.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

/**
 * Sharepreference 常用的操作类
 */
public class SPUtil {
    public static final String USER = "user";
    public static final String MSG = "msg";
    public static final String SETTINGS = "settings";
    public static final String COL_DISABLED_IDS = "col_disabled_ids";
    public static final String COL_DISABLED_GROUP_IDS = "col_disabled_group_ids";
    public static final java.lang.String DB_LOGIN = "login";
    public static final String SMS_TIME = "sms_time";
    public static final String FIRST_PREF = "first_pref";
    public static final java.lang.String LOGIN = "login";
    public static final String FILE = "file";
    public static final java.lang.String LOCATION = "location";
    public static final java.lang.String COOKIE = "COOKIE";
    public static final java.lang.String VERSION = "VERSION";
    public static final java.lang.String INDEX_DATA = "INDEX_DATA";
    public static final String TABTWO_STORE = "TABTWO_STORE";
    public static final java.lang.String PUSH = "PUSH";
    public static final String jSON_DATA = "json_data";
    private static Gson gson;

    private static SharedPreferences prefrence;
    private static SharedPreferences customPrefrence;

    private static SPUtil spUtil = null;
    private static SPUtil customSpUtil = null;


    public static SPUtil netInstance(Context applicationContext) {
        if (spUtil == null) {
            spUtil = new SPUtil();
            gson = new Gson();
        }

        if (prefrence == null) {
            String fileName = applicationContext.getString(R.string.app_name);
            prefrence = applicationContext.getSharedPreferences(fileName, 0);
        }
        return spUtil;
    }


    public static SPUtil netInstance() {
        if (spUtil == null) {
            spUtil = new SPUtil();
            gson = new Gson();
        }

        if (prefrence == null) {
            String fileName = BaseApp.getInstance().getApplicationContext().getString(R.string.app_name);
            prefrence = BaseApp.getInstance().getApplicationContext().getSharedPreferences(fileName, 0);
        }
        return spUtil;
    }

    private static SPUtil newInstance(String fileName) {
        if (customSpUtil == null) {
            customSpUtil = new SPUtil();
            gson = new Gson();
        }

        if (customPrefrence == null) {
            customPrefrence = BaseApp.getInstance().getApplicationContext().getSharedPreferences(fileName, 0);
        }
        return customSpUtil;
    }

    public static SPUtil putString(String key, String value) {
        Editor editor = prefrence.edit();
        editor.putString(key, value);
        editor.apply();

        return spUtil;
    }


    public static SPUtil putCusString(String fileName, String key, String value) {
        newInstance(fileName);
        Editor editor = customPrefrence.edit();
        editor.putString(key, value);
        editor.apply();

        return customSpUtil;
    }

    public static String getString(String key) {
        return prefrence.getString(key, "");
    }

    public static String getCusString(String fileName, String key) {
        newInstance(fileName);

        return customPrefrence.getString(key, "");
    }

    public static String getString(String key, String defaultStr) {
        return prefrence.getString(key, defaultStr);
    }

    public static SPUtil putBoolean(String key, Boolean value) {
        Editor editor = prefrence.edit();
        editor.putBoolean(key, value);
        editor.apply();
        return spUtil;
    }

    public static boolean getBoolean(String key) {
        return prefrence.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return prefrence.getBoolean(key, defaultValue);
    }

    public static boolean getBooleanWithTrue(String key) {
        return prefrence.getBoolean(key, true);
    }

    public static void putLong(String key, Long value) {
        Editor editor = prefrence.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(String key) {
        return prefrence.getLong(key, 0L);
    }

    public static SPUtil putInt(String key, Integer value) {
        Editor editor = prefrence.edit();
        editor.putInt(key, value);
        editor.apply();

        return spUtil;
    }

    public static int getInt(String key) {
        return prefrence.getInt(key, 0);
    }

    public static SPUtil putFloat(String key, Float value) {
        Editor editor = prefrence.edit();
        editor.putFloat(key, value);
        editor.apply();
        return spUtil;
    }

    public static float getFloat(String key) {
        return prefrence.getFloat(key, 0.0F);
    }


    public static SPUtil put(String key, Object value) {
        if (value != null) {
            String json = gson.toJson(value);
            putString(key, json);
        }
        return spUtil;
    }

    public static <T> T netInstance(String key, Class<T> clazz) {
        String string = getString(key);
        if (!isEmpty(string)) {
            return gson.fromJson(string, clazz);
        } else {
            return null;
        }
    }

    public static <T> ArrayList<T> netInstance(String key, Type type) {
        String string = getString(key);
        if (!isEmpty(string)) {
            ArrayList<T> ts = gson.fromJson(string, type);
            return ts;
        } else {
            return null;
        }
    }

    public static void remove(String key) {
        Editor editor = prefrence.edit();
        editor.remove(key);
        editor.apply();
    }


    /**
     * @param type 保存的语言类型
     * @param ac
     */
    public static void saveLastUpdateData(int type, Context ac) {
        if (ac == null) {
            return;
        }
        SharedPreferences sp = ac.getSharedPreferences("LANGUAGE", ac.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("LaUpdate", type);
        editor.commit();
    }


    /**
     * @param ac
     *
     * @return 获得保存的语言, 默认中文2, 1是英文，-1的时候是第一次，没有设置
     */
    public static int getLastUpdateData(Context ac) {

        int type;
        if (ac == null) {
            return 2;
        }
        try {
            SharedPreferences sp = ac.getSharedPreferences("LANGUAGE", ac.MODE_PRIVATE);
            type = sp.getInt("LaUpdate", -1);
            return type;
        } catch (Exception e) {
            type = -1;

        }
        return type;
    }


    /**
     * @param a
     *
     * @return 切换语言，部分
     */
    public static String getSwitchLanguage(Context a) {
        String url;
        if (SPUtil.getLastUpdateData(a) == 1) {//英文
            url = Constants.BASE_ENURL;
        } else {
            url = Constants.BASE_URL_MYNC;
        }
        return url;
    }


    /**
     * @param o
     * @param key 将对象保存在sp中
     */
    public static void saveObjectToSharedPreferences(Object o, String key) {
        try {

            Editor editor = prefrence.edit();
            //第一步:将对象保存至对象输出流ObjectOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            //第二步:将ByteArrayOutputStream中的对象转换成字节数组byte
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byte[] base64ByteArray = Base64.encode(byteArray, Base64.DEFAULT);
            //第三步:将Base64字节数组转换成String且保存至SharedPreferences
            String userString = new String(base64ByteArray);
            editor.putString(key, userString);
            editor.commit();
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    /**
     * 从SharedPreferences获取对象
     */
    public static <T> T getObjectFromSharedPreferences(String key) {
        try {
            //第一步:从SharedPreferences取出字符串形式的Object
            String userString = prefrence.getString(key, "");
            //第二步:将字符串转换成Base64字节数组
            byte[] byteArray = userString.getBytes();
            byte[] base64ByteArray = Base64.decode(byteArray, Base64.DEFAULT);
            //第三步:将字节数组存入ObjectInputStream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64ByteArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            //第四步:从ObjectInputStream中读取出对象Object
            return (T) objectInputStream.readObject();

        } catch (Exception e) {

        }
        return null;

    }


    /**
     * 获取一个对象
     * @param context
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> T getObject(Context context, Class<T> clazz) {
        String key = getKey(clazz);
        String json = getString(context, key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 保存一个对象
     * @param context
     * @param object
     */
    public static void putObject(Context context, Object object) {
        String key = getKey(object.getClass());
        Gson gson = new Gson();
        String json = gson.toJson(object);
        putString(context, key, json);
    }

    public static String getKey(Class<?> clazz) {
        return clazz.getName();
    }

    private static final String DEFAULT_SP_NAME = "default_sp";


    public static void putString(Context context, String key, String value) {
//        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefrence.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(Context context, String key, String defValue) {
//        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        return prefrence.getString(key, defValue);
    }


}
