package com.ybkj.demo.bean;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/1/24.
 * Email 15384030400@163.com
 */
public class ProgressMessage {
    public static final String SPLASH_UPDATE = "splashUpdate"; //
    public static final String SHOW = "show"; //显示dialog
    public static final String UPDATE = "update"; //更新
    public static final String COMPLETE = "complete";//完成

    public static final String UPDATE_MD5 = "intent_md5";
    public static final String UPDATE_URL = "apkUrl";
    public static final String UPDATE_TAG = "tag";


    private String msg;//消息内容
    private String tag; //消息类型
    private int progress;//进度

    public ProgressMessage(String tag, String msg, int progress) {
        super();
        this.tag = tag;
        this.msg = msg;
        this.progress = progress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
