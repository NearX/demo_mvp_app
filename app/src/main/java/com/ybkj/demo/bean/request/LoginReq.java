package com.ybkj.demo.bean.request;

import com.ybkj.demo.bean.BaseReq;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录请求实体类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class LoginReq extends BaseReq {

    /**
     * phoneCode : +86
     * phoneNumber : 13200000002
     * password : 111111
     */

    private String phoneCode;
    private String phoneNumber;
    private String password;

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
