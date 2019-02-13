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
import com.ybkj.demo.ui.view.PlaneProgressBar;
import com.ybkj.demo.utils.ResourcesUtil;

/**
 * Description 更新下载进度弹框
 * Author Ren Xingzhi
 * Created on 2019/1/24.
 * Email 15384030400@163.com
 */
public class UpdateProgressDialog extends Dialog {

    private PlaneProgressBar progressBar;
    private String version;

    public UpdateProgressDialog(@NonNull Context context, String version) {
        super(context);
        this.version = version;
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
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.x288));
    }

    private void initView() {
        progressBar = findViewById(R.id.progress);
        TextView versionTv = findViewById(R.id.version_tv);
        versionTv.setText("更新版本：V" + version);
    }

    /**
     * @param width 对话框的宽度
     */
    public void setAlertDialogWidth(int width) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
        }
    }

    public void updateProgress(int progress) {
        progressBar.setProgress(progress);
    }
}
