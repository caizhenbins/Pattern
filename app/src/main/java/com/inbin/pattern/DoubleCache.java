package com.inbin.pattern;

import android.graphics.Bitmap;
import android.widget.Button;

/**
 * Created by InBin on 2016/11/19.
 */
public class DoubleCache {

    ImageCache mMemoryCache = new ImageCache();
    DiskCache mDiskCache = new DiskCache();
    //先从缓存中获取照片
    public Bitmap get(String url){
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null){
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
    //将照片缓存到内存和SD卡中
    public void put(String url, Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }

}
