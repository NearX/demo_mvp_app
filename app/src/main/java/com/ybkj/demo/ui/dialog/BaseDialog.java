package com.ybkj.demo.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  BaseDialog
 * - @Time:  2018/8/21
 * - @Emaill:  380948730@qq.com
 */
public class BaseDialog extends Dialog {
    private final View mView;
    Activity activity;

    public BaseDialog(Activity context, int theme, int layoutResId) {
        super(context, theme);
        this.activity = context;
        mView = LayoutInflater.from(getContext()).inflate(layoutResId, null);
        setContentView(mView);
        super.setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(mView);
    }

    protected void initContentView(View mView) {
    }

    public <T extends View> T getView(int viewId, Class<T> clazz) {
        return (T) mView.findViewById(viewId);
    }

    /**
     * 设置dialog宽高
     *
     * @param width
     * @param height
     */
    public void setAlertDialogSize(int width, int height) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
            lp.width = width;
            lp.height = height;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 只设置对话框的宽度
     *
     * @param width
     */
    public void setAlertDialogWidth(int width) {
//        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        getWindow().setLayout(width, attrs.height);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 只设置对话框的高度
     *
     * @param height
     */
    public void setAlertDialogHeight(int height) {
        if (height <= 0) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            if (getWindow() != null) {
                lp.copyFrom(getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                getWindow().setAttributes(lp);
            }
        } else {
            if (getWindow() != null) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = height;
                getWindow().setAttributes(lp);
            }
        }
    }


    /**
     * 设置对话框的位置  例：Gravity.BOTTOM
     *
     * @param gravity
     */
    public void setAlertDialogGravity(int gravity) {
        //定义宽度
        if (getWindow() != null) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.gravity = gravity;
            getWindow().setAttributes(attrs);
        }
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        super.dismiss();
        if (isShowDefaultBackground()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1.0f;
            lp.dimAmount = 1.0f;
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

    }

    protected boolean isShowDefaultBackground() {
        return true;
    }

    /**
     * 显示对话框
     */
    public void show() {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (isShowDefaultBackground()) {
            lp.alpha = 0.5f;
            lp.dimAmount = 0.5f;
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        super.show();
    }

    /**
     * 设置动画
     *
     * @param style
     */
    public void setAnimation(int style) {
        Window w = getWindow();
        if (w != null) {
            w.setWindowAnimations(style);
        }
    }

}
