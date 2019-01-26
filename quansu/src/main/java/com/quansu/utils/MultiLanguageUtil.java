package com.quansu.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Shi on 2018/2/28.
 */

public class MultiLanguageUtil {
    private static final String TAG = "MultiLanguageUtil";
    private static MultiLanguageUtil instance;
    private Context mContext;

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 设置语言
     */
    public void setConfiguration(Context context) {
        mContext = context;
        if (instance == null) {
            return;
        }
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!

    }


    /**
     * 如果不是英文、简体中文，默认返回简体中文
     *
     * @return
     */


    private Locale getLanguageLocale() {
        int languageType;
        try {
            languageType = SPUtil.getLastUpdateData(mContext);

        } catch (Exception e) {
            //没有值
            languageType = -1;
        }

        if (languageType == -1) {
            //没有设置--跟随系统（只有一次）
            Locale sysType = getSysLocale();
            if (sysType.getLanguage().equals("en")) {

                //        //保存语言
                if (mContext != null) {
                    SPUtil.saveLastUpdateData(1, mContext);//保存语音的类型
                }


                return Locale.ENGLISH;
            } else if (sysType.getLanguage().equals("fr")) {//法语(如果是法语的话默认返回英语)

                if (mContext != null) {
                    SPUtil.saveLastUpdateData(1, mContext);//保存语音的类型
                }
                return Locale.ENGLISH;
            } else if (TextUtils.equals(sysType.getLanguage(), Locale.CHINA.getLanguage())) { //zh
                if (TextUtils.equals(sysType.getCountry(), Locale.CHINA.getCountry())) {  //适配华为mate9  zh_CN_#Hans
                    if (mContext != null) {
                        SPUtil.saveLastUpdateData(2, mContext);//保存语音的类型
                    }
                    return Locale.SIMPLIFIED_CHINESE;
                }
            } else {

                if (mContext != null) {
                    SPUtil.saveLastUpdateData(1, mContext);//保存语音的类型
                }
                return Locale.SIMPLIFIED_CHINESE;
            }


        } else {

            if (languageType == LanguageType.LANGUAGE_EN) {//英文
                return Locale.ENGLISH;
            } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {//中文
                return Locale.SIMPLIFIED_CHINESE;
            }

        }


        getSystemLanguage(getSysLocale());
        return Locale.SIMPLIFIED_CHINESE;
    }


    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //7.0以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        if (Build.VERSION.SDK_INT < 24) {
            return mContext.getResources().getConfiguration().locale;
        } else {
            return mContext.getResources().getConfiguration().getLocales().get(0);
        }
    }

    /**
     * 更新语言
     */
    public void updateLanguage(int savedLanguageType) {
        SPUtil.saveLastUpdateData(savedLanguageType, mContext);//保存语音的类型
        MultiLanguageUtil.getInstance().setConfiguration(mContext);

    }

    /**
     * 获取到用户保存的语言类型
     *
     * @return
     */
    public int getLanguageType() {

        int languageType = SPUtil.getLastUpdateData(mContext);

        if (languageType == LanguageType.LANGUAGE_EN) {
            return LanguageType.LANGUAGE_EN;
        } else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
        }
        return languageType;
    }


    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 获取选择的语言设置
     *
     * @param context
     *
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        int languageType = SPUtil.getLastUpdateData(context);

        switch (languageType) {

            case 1:
                return Locale.ENGLISH;

            case 2:
                return Locale.SIMPLIFIED_CHINESE;
            default:
                return Locale.SIMPLIFIED_CHINESE;

        }
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }


}
