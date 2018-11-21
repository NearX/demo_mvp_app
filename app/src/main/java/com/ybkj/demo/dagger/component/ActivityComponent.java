package com.ybkj.demo.dagger.component;

import android.app.Activity;
import android.content.Context;

import com.ybkj.demo.dagger.module.ActivityModule;
import com.ybkj.demo.dagger.scope.ActivityScope;
import com.ybkj.demo.module.MainActivity;
import com.ybkj.demo.module.login.activity.LoginActivity;
import com.ybkj.demo.module.login.activity.SplashActivity;
import com.ybkj.demo.module.mine.CheckVersionActivity;
import com.ybkj.demo.module.mine.ModifyAccountActivity;
import com.ybkj.demo.module.mine.ModifyPsdActivity;
import com.ybkj.demo.module.mine.PersonalActivity;

import dagger.Component;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  Activity的Component
 * 主要作用是将@Inject标注的Presenter和@Module标注的ActivityModule联系起来，从@Module中获取依赖，
 * 并将依赖注入给@Inject标注的对象。
 * - @Time:  2018/9/5
 * - @Emaill:  380948730@qq.com
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    Context getContext();

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);

    void inject(PersonalActivity personalActivity);

    void inject(ModifyPsdActivity modifyPsdActivity);

    void inject(CheckVersionActivity checkVersionActivity);

    void inject(ModifyAccountActivity modifyAccountActivity);


}
