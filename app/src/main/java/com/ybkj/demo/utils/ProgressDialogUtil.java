package com.ybkj.demo.utils;

import android.app.Activity;
import android.content.Context;

import com.ybkj.demo.R;
import com.ybkj.demo.ui.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  网络请求的对话框显示控制工具
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class ProgressDialogUtil {

    private static WeakReference<Context> contextWeakReference;  //弱引用
    private static WeakReference<LoadingDialog> waitDialog;   //弱引用

    public static LoadingDialog initProgressDialog(Context mContext, boolean cancel) {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }

        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing()) {
            return null;
        }
        contextWeakReference = new WeakReference<>(mContext);

        LoadingDialog loadingDialog = new LoadingDialog(contextWeakReference.get(), R.style.common_dialog);
        loadingDialog.setAlertDialogSize((int) ResourcesUtil.getDimen(R.dimen.x100), (int) ResourcesUtil
                .getDimen(R.dimen.y100));
        loadingDialog.setMessage(mContext.getString(R.string.loading));
        waitDialog = new WeakReference<>(loadingDialog);
        loadingDialog.setCancelable(cancel);
        return loadingDialog;
    }

    public static void showWaitDialog() {
        if (waitDialog != null && waitDialog.get() != null && !waitDialog.get().isShowing()) {
            waitDialog.get().show();
        }
    }

    /**
     * 取消等待dialog
     */
    public static void stopWaitDialog() {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }
    }
}
