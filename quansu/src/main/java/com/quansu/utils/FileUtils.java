package com.quansu.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aspsine on 16/4/7.
 */
public class FileUtils {

    private static final String HTTP_CACHE_DIR = "http";
    private final static String PATTERN = "yyyyMMddHHmmss";
    private static final String PIC_PATH = "/IMAGES/";


    public static File getPicFile(Context context) {
        return getFile(context, PIC_PATH);
    }


    public static File getFile(Context context, String filePath) {

        String timeStamp = new SimpleDateFormat(PATTERN, Locale.CHINA).format(new Date());

        String externalStorageState = Environment.getExternalStorageState();

        File dir = new File(Environment.getExternalStorageDirectory() + filePath);

        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = context.getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    public static File getAppFile(Context context, String fileName) {
        String externalStorageState = Environment.getExternalStorageState();
        File dir = new File(Environment.getExternalStorageDirectory() + "/com.muxi.ant/");
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, fileName);

        } else {
            File cacheDir = context.getCacheDir();
            return new File(cacheDir, fileName);
        }

    }


    public static File getOwnFile(Context context, String fileName) {
        return new File(context.getCacheDir(), fileName);
    }

    /**
     * 通知图库有新图片添加
     *
     * @param context
     */
    public static void notiAlbum(Context context, String path) {
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


    public static final File getHttpCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(context.getCacheDir(), HTTP_CACHE_DIR);
        }
        return new File(context.getCacheDir(), HTTP_CACHE_DIR);
    }

    /**
     * 获取文件后缀,不包括“.”
     *
     * @param pathOrUrl
     *
     * @return
     */
    public static String getExtension(String pathOrUrl) {
        int dotPos = pathOrUrl.lastIndexOf('.');
        if (0 <= dotPos) {
            return pathOrUrl.substring(dotPos + 1);
        } else {
            return "ext";
        }
    }

    /**
     * 获取文件的MIME类型
     *
     * @param pathOrUrl
     *
     * @return
     */
    public static String getMimeType(String pathOrUrl) {
        String ext = getExtension(pathOrUrl);
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String mimeType;
        if (map.hasExtension(ext)) {
            mimeType = map.getMimeTypeFromExtension(ext);
        } else {
            mimeType = "*/*";
        }
        return mimeType;
    }


    public static String getName(String pathOrUrl) {
        int pos = pathOrUrl.lastIndexOf('/');
        return pathOrUrl.substring(pos + 1);
    }

    /**
     * 创建文件名（包括扩展名）
     *
     * @param pathOrUrl
     *
     * @return
     */
    public static String createName(String pathOrUrl) {
        return createName(pathOrUrl, false);
    }

    public static String createName(String pathOrUrl, boolean useHash) {
        if (useHash) {
            return pathOrUrl.replace("/", "_") + "." + getExtension(pathOrUrl);
        }

        int pos = pathOrUrl.lastIndexOf('/');
        if (0 <= pos) {
            return MD5.md5(String.valueOf(System.currentTimeMillis()) + pathOrUrl.substring(pos + 1)) + "." + getExtension(pathOrUrl);
        } else {
            return MD5.md5(String.valueOf(System.currentTimeMillis())) + "." + getExtension(pathOrUrl);
        }
    }

    /**
     * 获取文件名（不包括扩展名）
     *
     * @param pathOrUrl
     *
     * @return
     */
    public static String getNameExcludeExtension(String pathOrUrl) {
        try {
            String name = createName(pathOrUrl);
            return name.substring(0, name.lastIndexOf('.'));
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 创建图片名
     *
     * @param path 保存路径
     *
     * @return 时间命名的jpg
     */
    public static File createFile(String path) {
        File outDir = new File(path);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        return new File(outDir.getPath(), System.currentTimeMillis() + ".jpg");
    }

    /**
     * 给bitmap  和文件名 进行保存
     *
     * @param bm
     * @param i
     *
     * @return
     */
    public static String saveFile(Bitmap bm, String i) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/pregnancy");
        String path = mediaStorageDir.getPath() + "/" + i + ".jpg";

        File myCaptureFile = new File(path);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return path;
    }


}


