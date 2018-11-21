package com.ybkj.demo.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ybkj.demo.R;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  BaseButtonDialog
 * - @Time:  2018/8/21
 * - @Emaill:  380948730@qq.com
 */
public abstract class BaseButtonDialog extends BaseDialog {

    public View contentView;
    protected TextView tvTitle;
    protected Button btnCancel;
    protected View btnButton;
    protected String titleStr;
    protected Button btnConfirm;
    protected String confirmButtonStr;
    protected String cancelButtonStr;
    private FrameLayout flContent;
    private OnCancelButtonClickListener onCancelButtonClickListener;
    private OnConfirmButtonClickListener onConfirmButtonClickListener;


    public BaseButtonDialog(Activity context) {
        super(context, R.style.common_dialog, R.layout.dialog_button_base);
        contentView = setContentView();
    }

    /**
     * 取消按钮回调监听
     *
     * @param onCancelButtonClickListener
     */
    public void setOnCancelButtonClickListener(OnCancelButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
    }

    /**
     * 确认按钮回调监听
     *
     * @param onConfirmButtonClickListener
     */
    public void setOnConfirmButtonClickListener(OnConfirmButtonClickListener onConfirmButtonClickListener) {
        this.onConfirmButtonClickListener = onConfirmButtonClickListener;
    }

    public void setTitleText(String titleStr) {
        this.titleStr = titleStr;
        if (tvTitle != null) {
            tvTitle.setText(titleStr);
        }
    }

    /**
     * 设置底部按钮是否显示
     *
     * @param visible
     */
    public void setButtonVisible(int visible) {
        if (btnButton != null) {
            btnButton.setVisibility(visible);
        }
    }

    public void setConfirmButtonText(String confirmButtonStr) {
        this.confirmButtonStr = confirmButtonStr;
    }

    public void setCancelButtonText(String cancelButtonStr) {
        this.cancelButtonStr = cancelButtonStr;
    }

    protected abstract View setContentView();

    protected abstract void initContentView(View contentView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
        //此处可统一设置宽度
//        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.x360));

    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        flContent = findViewById(R.id.fl_content);
        btnCancel = findViewById(R.id.btn_negative);
        btnConfirm = findViewById(R.id.btn_positive);
        btnButton = findViewById(R.id.button_btn);
        btnCancel.setOnClickListener(v -> {
            if (onCancelButtonClickListener != null) {
                onCancelButtonClickListener.cancel(this, v);
            }
            dismiss();
        });
        if (cancelButtonStr != null) {
            btnCancel.setText(cancelButtonStr);
        }
        btnConfirm.setOnClickListener(v -> {
            if (onConfirmButtonClickListener != null) {
                onConfirmButtonClickListener.confirm(this, v);
            }
        });
        if (confirmButtonStr != null) {
            btnConfirm.setText(confirmButtonStr);
        }
        initContentView(contentView);
        if (titleStr != null && !titleStr.equals("")) {
            tvTitle.setText(titleStr);
        }
        if (confirmButtonStr != null && !confirmButtonStr.equals("")) {
            btnConfirm.setText(confirmButtonStr);
        }

        if (contentView != null) {
            flContent.removeAllViews();
            flContent.addView(contentView);
        }
    }

    public <T extends View> T getContentView(int viewId, Class<T> clazz) {
        return (T) contentView.findViewById(viewId);
    }

    /**
     * 取消按钮回调监听
     */
    public interface OnCancelButtonClickListener {
        void cancel(Dialog dialog, View view);
    }

    /**
     * 确认按钮回调监听
     */
    public interface OnConfirmButtonClickListener {
        void confirm(Dialog dialog, View view);
    }
}
