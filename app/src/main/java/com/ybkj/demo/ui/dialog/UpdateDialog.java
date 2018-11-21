package com.ybkj.demo.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.utils.ResourcesUtil;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  UpdateDialog
 * - @Time:  2018/8/23
 * - @Emaill:  380948730@qq.com
 */

public class UpdateDialog extends BaseDialog {

    private OnPositiveClickListener onPositiveClickListener;
    private TextView update_cotent_tv;
    private OnNegativeClickListener onNegativeClickListener;
    private String versionName;
    private int type;

    public UpdateDialog(Activity context, String versionName, int type) {
        super(context, R.style.update_dialog, R.layout.dialog_update_app);
        this.versionName = versionName;
        this.type = type;
    }

    @Override
    protected void initContentView(View mView) {
        if (type == 2) {
            setCanceledOnTouchOutside(true);
            setCancelable(true);
        } else if (type == 1) {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        update_cotent_tv = mView.findViewById(R.id.update_cotent_tv);
        ImageView update_later_tv = mView.findViewById(R.id.update_later_tv);
        TextView update_now_tv = mView.findViewById(R.id.update_now_tv);
        TextView updateTittleTv = mView.findViewById(R.id.update_title_tv);//标题

        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.x289));

        update_later_tv.setOnClickListener(v -> {
            dismiss();
            if (onNegativeClickListener != null) {
                onNegativeClickListener.onNegativeClick(v);
            }
        });
        update_now_tv.setOnClickListener(v -> {
            if (onPositiveClickListener != null) {
                onPositiveClickListener.onPositiveClick(v);
            }
        });
        updateTittleTv.setText("是否升级到" + versionName + "版本");
    }

    public void setOnPositiveClickListener(OnPositiveClickListener listener) {
        this.onPositiveClickListener = listener;
    }

    public void setNegativeClickListener(OnNegativeClickListener listener) {
        this.onNegativeClickListener = listener;
    }

    public void setContentText(String s) {
        if (update_cotent_tv != null) {
            update_cotent_tv.setText(s);
        }
    }


    public interface OnPositiveClickListener {
        void onPositiveClick(View v);
    }

    public interface OnNegativeClickListener {
        void onNegativeClick(View v);
    }

}
