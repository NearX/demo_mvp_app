package com.ybkj.demo.bean.response;

import java.io.Serializable;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录返回实体类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class LoginRes implements Serializable {


    private int isHeader;
    private int isLeader;
    private String companyName;
    private UserBean user;
    private String token;

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean implements Serializable {
        /**
         * id : 299
         * userAccount : +13200000002
         * phoneCode : +86
         * phoneNumber : 13200000002
         * password : null
         * userName : 三三
         * idCard : 111111111111111111
         * renameState : 0
         * subjection : 1
         * status : 1
         * remark : null
         * addTime : 1540812310000
         * updateTime : null
         * lastCompanyId : 26
         */

        private int id;
        private String userAccount;
        private String phoneCode;
        private String phoneNumber;
        private Object password;
        private String userName;
        private String idCard;
        private int renameState;
        private int subjection;
        private int status;
        private Object remark;
        private long addTime;
        private Object updateTime;
        private String lastCompanyId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

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

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getRenameState() {
            return renameState;
        }

        public void setRenameState(int renameState) {
            this.renameState = renameState;
        }

        public int getSubjection() {
            return subjection;
        }

        public void setSubjection(int subjection) {
            this.subjection = subjection;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getLastCompanyId() {
            return lastCompanyId;
        }

        public void setLastCompanyId(String lastCompanyId) {
            this.lastCompanyId = lastCompanyId;
        }
    }
}
