package com.ybkj.demo.dagger.module;


import android.text.TextUtils;

import com.ybkj.demo.BuildConfig;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.manager.UserDataManager;
import com.ybkj.demo.module.changeurl.ChangeUrlActivity;
import com.ybkj.demo.net.api.ApiService;
import com.ybkj.demo.net.api.BaseNetFunction;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.SPHelper;
import com.ybkj.demo.utils.StringUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  通过dagger方式创建网络请求所需要的对象
 *
 * @Singleton全局单例
 * @Provides 为创建Presenter提供依赖的
 * - @Time:  2018/9/5
 * - @Emaill:  380948730@qq.com
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    BaseNetFunction provideBaseNetFunction() {
        return new BaseNetFunction();
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
                LogUtil.i("OkHttp --> " + message);
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置Http缓存
        //Cache cache = new Cache(new File(CommonUtil.getCacheDir(), "httpCache"), 1024 * 1024 * 10);
        return builder.addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new SleepInterceptor())
                //.cache(cache)
                .connectTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .readTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .writeTimeout(Constants.connectionTime, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        String url = SPHelper.getInstance().getString(ChangeUrlActivity.CHANGE_URL_KEY);
        if (StringUtil.isNotNull(url)) {
            return createRetrofit(builder, client, url);
        }
        return createRetrofit(builder, client, Constants.DEFAULT_BASE_URL);
    }

    @Provides
    @Singleton
    ApiService provideLoginService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    /**
     * 创建Retrofit
     *
     * @param builder
     * @param client
     * @param url
     * @return
     */
    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder.baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * token&sign拦截器
     */
    private class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            String token = UserDataManager.getToken();
            Request.Builder builder = oldRequest.newBuilder();
            if (!TextUtils.isEmpty(token)) {
                builder.addHeader(Constants.TOKEN_KEY, token);
                LogUtil.i("token=" + token);
            }
            return chain.proceed(builder.build());
        }
    }

    /**
     * 平滑请求时间拦截器
     */
    private class SleepInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long start = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            if (System.currentTimeMillis() - start < 1000) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

}
