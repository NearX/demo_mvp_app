package com.ybkj.demo.bean;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  网络请求返回基类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class BaseResponse<T> {
    private int code;//1成功
    private String message;//成功的返回信息
    private T data;//具体数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
