package com.quansu.utils.file;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

/**
 * Created by xianguangjin on 16/8/16.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class LubanUtils {

    ArrayList<String> imgs=new ArrayList<>();


    public void compressFilesSync(Context context, ArrayList<String> strings, OnCompressFinishListener onCompressFinishListener) {
        //size = strings.size();

        if(imgs.size()>0){
            imgs.clear();
        }
        imgs.addAll(strings);


        setComplessfile(context,strings.get(0),0,strings,onCompressFinishListener);


    }






    //一张一张的上传
    public void setComplessfile(Context context,String path,int index,ArrayList<String> strings,OnCompressFinishListener onCompressFinishListener){

        compressFileTo(context, strings.get(index), new OnCompressFileFinishListener() {
            @Override
            public void onFinishedTo(String filePath) {
                if (isEmpty(filePath)) {
                    strings.set(index, "0");

                } else {
                    strings.set(index, filePath);
                }
               if(index==strings.size()-1){
                   onCompressFinishListener.onFinished(strings);
               }else{
                   setComplessfile(context,strings.get(index+1),index+1,strings,onCompressFinishListener);

               }



            }


        });


    }






    public static void compressFileTo(Context context, String path, OnCompressFileFinishListener onCompressFileFinishListener) {


        Luban.get(context)
                .load(new File(path))
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        if (onCompressFileFinishListener != null) {

                            //onCompressFileFinishListener.onFinished(file.getAbsolutePath());
                            onCompressFileFinishListener.onFinishedTo(file.getAbsolutePath());


                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onCompressFileFinishListener != null) {
                           // onCompressFileFinishListener.onFinished(null);
                            onCompressFileFinishListener.onFinishedTo(null);

                        }
                    }
                })
                .launch();

    }


}
