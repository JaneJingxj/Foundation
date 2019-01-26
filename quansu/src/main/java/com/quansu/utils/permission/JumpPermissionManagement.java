package com.quansu.utils.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import com.ysnows.quansu.BuildConfig;
import com.ysnows.quansu.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Shi on 2017/10/13.
 */

public class JumpPermissionManagement {

    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "Letv";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 此函数可以自己定义
     * @param activity
     */
    public static void GoToSetting(Activity activity){
        switch (Build.MANUFACTURER){
            case MANUFACTURER_HUAWEI:
                Huawei(activity);
                break;
            case MANUFACTURER_MEIZU:
                Meizu(activity);
                break;
            case MANUFACTURER_XIAOMI:
                Xiaomi(activity);
                break;
            case MANUFACTURER_SONY:
                Sony(activity);
                break;
            case MANUFACTURER_OPPO:
                OPPO(activity);
                break;
            case MANUFACTURER_LG:
                LG(activity);
                break;
            case MANUFACTURER_LETV:
                Letv(activity);
                break;
            default:
                ApplicationInfo(activity);
                Log.e("goToSetting", "目前暂不支持此系统");
                break;
        }
    }

    public static void Huawei(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }    }

    public static void Meizu(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }    }

    public static void Xiaomi(Activity activity) {
//        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
//        intent.setComponent(componentName);
//        intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);

        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");

        String rom = getMiuiVersion();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if(Build.VERSION.SDK_INT >= 9){
//                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//            } else if(Build.VERSION.SDK_INT <= 8){
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
//                intent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
//            }

            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);


        }


        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }    }

    public static void Sony(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }    }

    public static void OPPO(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }    }

    public static void LG(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }
    }

    public static void Letv(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }
    }

    /**
     * 只能打开到自带安全软件
     * @param activity
     */
    public static void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        try{

            activity.startActivity(intent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }
    }

    /**
     * 应用信息界面
     * @param activity
     */
    public static void ApplicationInfo(Activity activity){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        try{

            activity.startActivity(localIntent);
        }catch(Exception exception) {

            //exception.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.open_corresponding_permissions), Toast.LENGTH_SHORT).show();


        }
    }

    /**
     * 系统设置界面
     * @param activity
     */
    public static void SystemConfig(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }




    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {

        }
        return line;
    }


}
