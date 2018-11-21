package com.ybkj.demo.dagger.scope;

import javax.inject.Qualifier;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  HasParams
 *
 * @Qualifier 告诉Dagger依赖需求方 创建数据的时候使用哪个依赖提供方
 * 主要作用就是区分@inject的构造函数
 * - @Time:  2018/9/6
 * - @Emaill:  380948730@qq.com
 */
@Qualifier
public @interface HasParams {
}

