package com.ybkj.demo.manager;


import com.ybkj.demo.bean.response.LoginRes;
import com.ybkj.demo.utils.preferences.PreferencesUtils;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  用户信息管理
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class UserDataManager {
    private static String KEY_USER_INFO = "login_info";

    /**
     * Save getCompanyList info.
     *
     * @param loginRes the getCompanyList res
     */
    public static void saveLoginInfo(LoginRes loginRes) {
        PreferencesUtils.saveObject(KEY_USER_INFO, loginRes);
    }

    /**
     * Gets getCompanyList info.
     *
     * @return the getCompanyList info
     */
    public static LoginRes getLoginInfo() {
        return (LoginRes) PreferencesUtils.getObject(KEY_USER_INFO, null);
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public static String getToken() {
        LoginRes loginInfo = getLoginInfo();
        if (loginInfo != null) {
            return loginInfo.getToken();
        }
        return null;
    }

    public static void clearLoginInfo() {
        saveLoginInfo(null);
    }
}
