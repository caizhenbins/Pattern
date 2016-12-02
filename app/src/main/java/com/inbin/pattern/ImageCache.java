package com.inbin.pattern;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by InBin on 2016/11/19.
 */
public class ImageCache {

    //图片缓存
    LruCache<String,Bitmap> mImageCache;
    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
        //计算可用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory /4;
        mImageCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() /1024;
            }
        };
    }
public void put(String url, Bitmap bitmap){
    mImageCache.put(url,bitmap);
}
    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
