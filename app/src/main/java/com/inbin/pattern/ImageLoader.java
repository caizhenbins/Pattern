package com.inbin.pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by InBin on 2016/11/19.
 */
public class ImageLoader {
    //内存图片缓存
    ImageCache mImageCache = new ImageCache();
    //SD卡缓存
    DiskCache mDiskCache = new DiskCache();
    //是否启用SD卡缓存
    boolean isUseDiskCache = false;
    //双缓存
    DoubleCache mDoubleCache = new DoubleCache();
    //是否使用双缓存
    boolean isUseDoubleCache = false;
    //线程池
    ExecutorService mExecuteService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //图片显示
    public void displayImage(final String url, final ImageView imageView) {
        //判断使用那种缓存
        Bitmap bitmap = null;
        if (isUseDoubleCache) {
            bitmap = mDoubleCache.get(url);
        }else if (isUseDiskCache){
            mDiskCache.get(url);
        }else {
            bitmap = mImageCache.get(url);
        }
        imageView.setTag(url);
        mExecuteService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url, bitmap);
            }
        });
    }

    //下载图片
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //判断是否使用SD卡缓存
    public void useDiskCache(boolean useDiskCache){
        isUseDiskCache = useDiskCache;
    }
    //判断是否使用Double缓存
    public void useDoubleCache(boolean useDoubleCache){
        isUseDoubleCache = useDoubleCache;
    }

}
