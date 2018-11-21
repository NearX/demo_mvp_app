package com.ybkj.demo.bean.response;

import com.ybkj.demo.bean.BaseResponse;

public class VersionRes extends BaseResponse {

    private int forceStatus;
    private String updateExplain;
    private String appUrl;
    private String newestVersion;

    public int getForceStatus() {
        return forceStatus;
    }

    public void setForceStatus(int forceStatus) {
        this.forceStatus = forceStatus;
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

    public String getNewestVersion() {
        return newestVersion;
    }

    public void setNewestVersion(String newestVersion) {
        this.newestVersion = newestVersion;
    }
}
