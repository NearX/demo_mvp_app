package com.ybkj.demo.net.exception;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.ybkj.demo.utils.LogUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  处理网络异常
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class HandlerException {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            LogUtil.i("httpException.code()=" + httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    ex.setMessage("网络错误");
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.setMessage("服务器异常");
                    break;
                default:
                    ex.setMessage("网络错误");
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.setMessage("数据解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.setMessage("网络连接失败");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.setMessage("证书验证失败");
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof ResponseThrowable) {
            return (ResponseThrowable) e;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            if (TextUtils.isEmpty(e.getMessage())) {
                ex.message = "未知错误...";
            } else {
                ex.message = e.getMessage();
            }
            return ex;
        }
    }

    public static class ResponseThrowable extends RuntimeException {
        public int code;
        public String message;
        private Object data;

        public <T> ResponseThrowable(String responseMessage, int code, T data) {
            super(responseMessage);
            this.message = responseMessage;
            this.code = code;
            this.data = data;
        }

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        public ResponseThrowable(String message) {
            super(message);
            this.message = message;
        }

        public ResponseThrowable(String message, int code) {
            super(message);
            this.message = message;
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        static final int SSL_ERROR = 1005;
    }

}
