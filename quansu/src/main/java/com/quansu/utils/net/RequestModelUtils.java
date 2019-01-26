package com.quansu.utils.net;

import com.quansu.utils.StringUtil;
import com.ysnows.quansu.BuildConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by xianguangjin on 2017/4/2.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class RequestModelUtils {

    public static HashMap<String, String> toMaps(Object o) {
        HashMap<String, String> hashMap = new HashMap<>();
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            try {
                Object value = field.get(o);
                if (value == null) {
                    continue;
                }

                if (value instanceof Integer && ((int) value) == -1) {
                    continue;
                }

                Method method = null;
                try {
                    method = o.getClass().getDeclaredMethod("set" + StringUtil.setterName(field.getName()), getClazz(value));
                } catch (NoSuchMethodException e) {
                    if (BuildConfig.DEBUG) e.printStackTrace();
                    continue;
                }
                if (method != null) {
                    hashMap.put(field.getName(), value + "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return hashMap;
    }

    public static Class<?> getClazz(Object value) {
        if (value instanceof Integer) {
            return int.class;
        } else if (value instanceof String) {
            return String.class;
        } else if (value instanceof Float) {
            return float.class;
        } else if (value instanceof Double) {
            return double.class;
        }
        return null;
    }


}
