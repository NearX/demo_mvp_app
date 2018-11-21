package com.ybkj.demo.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.ybkj.demo.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  提供activity的依赖对象
 * - @Time:  2018/9/5
 * - @Emaill:  380948730@qq.com
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return activity;
    }
}
