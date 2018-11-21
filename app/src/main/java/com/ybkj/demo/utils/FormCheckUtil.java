package com.ybkj.demo.utils;

import android.text.TextUtils;

import com.ybkj.demo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  表单正确性验证
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class FormCheckUtil {

    public static boolean checkNullHalf(String text, String message) {

        if (TextUtils.isEmpty(text)) {
            ToastUtil.showShort(message + "不能为空");
            return true;
        }

        return false;
    }

    public static boolean checkNull(String text, String message) {

        if (TextUtils.isEmpty(text)) {
            ToastUtil.showShort(message);
            return true;
        }

        return false;
    }

    /**
     * 验证手机号格式
     *
     * @param areaCode
     * @param phoneNum
     * @return
     */
    public static boolean phoneCheck(String areaCode, String phoneNum) {

        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_phone_null));
            return true;
        }

        if (areaCode.startsWith(ResourcesUtil.getString(R.string.area_china))) {
            if (phoneNum.length() != 11) {
                ToastUtil.showShort(ResourcesUtil.getString(R.string.staff_phone_eleven));
                return true;
            }
        } else if (phoneNum.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.staff_phone_six));
            return true;

        }
        return false;
    }

    /**
     * 验证手机号格式
     *
     * @param phoneNum
     * @return
     */
    public static boolean phoneCheck(String phoneNum) {

        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_phone_null));
            return true;
        }

        if (phoneNum.length() != 11) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.staff_phone_eleven));
            return true;
        }
        return false;
    }

    /**
     * 验证所传验证码的格式
     *
     * @param verificationCode
     * @return
     */
    public static boolean verificationCodeCheck(String verificationCode) {
        if (TextUtils.isEmpty(verificationCode)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_code_null));
            return true;
        }
        if (verificationCode.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_code_min_six));
            return true;
        }
        return false;
    }

    /**
     * 验证登录密码的格式
     *
     * @param pwd
     * @returna
     */
    public static boolean pwdFormCheck(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_pass));
            return true;
        }
        if (pwd.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_pass_mix_six));
            return true;
        }
        return false;
    }

    /**
     * 验证登录密码的格式
     *
     * @param pwd
     * @returna
     */
    public static boolean pwdAgainFormCheck(String pwd, String pwdAgain) {
        if (!pwd.equals(pwdAgain)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.register_pass_equals_again));
            return true;
        }
        return false;
    }


    /**
     * 校验密码
     *
     * @param password 密码
     * @return
     */
    public static boolean checkAccountAndPwd(String password) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_pass));
            return true;
        }
        if (password.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.login_pass_mix_six));
            return true;
        }
        return false;
    }


    /**
     * 是否大陆手机
     *
     * @param str
     * @return
     */
    public static boolean isChinaPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}