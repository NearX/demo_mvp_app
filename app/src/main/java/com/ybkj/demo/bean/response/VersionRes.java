package com.ybkj.demo.bean.response;

import com.ybkj.demo.bean.BaseResponse;

public class VersionRes extends BaseResponse {

    private AppVersionBean appVersion;

    public AppVersionBean getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersionBean appVersion) {
        this.appVersion = appVersion;
    }

    public static class AppVersionBean {

        //记录id
        private int id;
        //应用类型： 1：代理加盟商；2：销售；3：公司体系；4：发货端
        private int useType;
        //更新类型，1：可用更新，2：强制更新
        private int type;
        //版本号
        private String versionNumber;
        //更新说明
        private String updateExplain;
        //下载地址
        private String appUrl;
        //状态 1：使用 -1：删除
        private int status;
        //添加时间
        private long addTime;
        //最近的强制更新的版本
        private String forceUpdatingVersionNumber;
        //md5
        private String updateKey;

        public String getUpdateKey() {
            return updateKey;
        }

        public void setUpdateKey(String updateKey) {
            this.updateKey = updateKey;
        }

        public String getForceUpdatingVersionNumber() {
            return forceUpdatingVersionNumber;
        }

        public void setForceUpdatingVersionNumber(String forceUpdatingVersionNumber) {
            this.forceUpdatingVersionNumber = forceUpdatingVersionNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUseType() {
            return useType;
        }

        public void setUseType(int useType) {
            this.useType = useType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(String versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getUpdateExplain() {
            return updateExplain;
        }

        public void setUpdateExplain(String updateExplain) {
            this.updateExplain = updateExplain;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }
    }
}