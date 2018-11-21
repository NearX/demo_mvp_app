package com.ybkj.demo.common;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  返回返回状态码标识
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class NetResponseCode {
    public final static int HMC_SUCCESS = 1;//成功
    public final static int HMC_SUCCESS_NULL = -1;//请求成功，但是data为空。自定义
    public static final int HMC_NETWORK_ERROR = -00000;//没有网络
    public static final int HMC_NO_LOGIN = 4;//未登录


}