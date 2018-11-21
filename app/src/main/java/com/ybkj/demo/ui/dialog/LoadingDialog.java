package com.ybkj.demo.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ybkj.demo.R;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  加载dialog
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class LoadingDialog extends Dialog {
    private TextView mMessage;
    private String mMessageStr = "加载中···";
    private Context mContext;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }


    //系统提供的 二个参数的
    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }


    //系统提供 带三个参数的
    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setTitle(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
        initView();
    }

    private void initView() {
        mMessage = findViewById(R.id.message);
        mMessage.setText(mMessageStr);
    }

    public void setMessage(String message) {
        mMessageStr = message;
    }

    public void setAlertDialogSize(int width, int height) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
        }
        lp.width = width;
        lp.height = height;
        getWindow().setAttributes(lp);
    }
}