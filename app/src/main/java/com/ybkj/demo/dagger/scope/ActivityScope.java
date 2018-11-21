package com.ybkj.demo.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  ActivityScope
 * 自定义@Scope注解    保持跟随绑定的Activity的生命周期内单例
 * - @Time:  2018/9/6
 * - @Emaill:  380948730@qq.com
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
