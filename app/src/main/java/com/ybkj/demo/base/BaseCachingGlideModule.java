package com.ybkj.demo.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.ybkj.demo.R;

import java.io.InputStream;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  Glide图片处理
 * - @Time:  2018/9/2
 * - @Emaill:  380948730@qq.com
 */
@GlideModule
public final class BaseCachingGlideModule extends AppGlideModule {
    private static final String IMAGE_CACHE_NAME = "image";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.glide_tag_id);
        //设置图片的显示格式ARGB_565
        //位图位数越高，存储的颜色信息越多，图像也就越逼真。大多数场景使用的是 ARGB_8888 和 RGB_565，
        // RGB_565 能够在保证图片质量的情况下大大减少内存 的开销，是解决 oom 的一种方法。
        //但是一定要注意 RGB_565 是没有透明度的，如果图片本身需要保留透明度，那么 就不能使用 RGB_565
        RequestOptions request = new RequestOptions().format(DecodeFormat.PREFER_RGB_565).encodeFormat(Bitmap
                .CompressFormat.PNG);
        builder.setDefaultRequestOptions(request);
        // 设置磁盘缓存为100M，缓存在内部缓存目录
        int cacheSize = 100 * 1024 * 1024;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, IMAGE_CACHE_NAME, cacheSize));

        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, IMAGE_CACHE_NAME, cacheSize));
        // 20%大的内存缓存作为 Glide 的默认值
        MemorySizeCalculator.Builder builder1 = new MemorySizeCalculator.Builder(context);
        MemorySizeCalculator calculator = builder1.build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.append(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory());
    }
}
