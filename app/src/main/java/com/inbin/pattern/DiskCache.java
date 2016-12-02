package com.inbin.pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by InBin on 2016/11/19.
 */
public class DiskCache {

    static String cacheDir = "sdcard/cache/";
    //从缓存中获取照片
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir + url);
    }
    //将图片缓存到内存中
    public void put(String url, Bitmap bmp){
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
