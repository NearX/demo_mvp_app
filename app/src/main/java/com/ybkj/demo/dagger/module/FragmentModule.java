package com.ybkj.demo.dagger.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ybkj.demo.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  FragmentModule
 * - 这里提供的是v4包下的fragment，不要使用V7下的
 * - @Time:  2018/9/6
 * - @Emaill:  380948730@qq.com
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    Context provideContext() {
        return fragment.getActivity();
    }
}
