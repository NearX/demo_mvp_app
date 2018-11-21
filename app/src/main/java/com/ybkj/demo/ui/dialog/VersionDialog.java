package com.ybkj.demo.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.utils.AppUpdateVersionCheckUtil;
import com.ybkj.demo.utils.ResourcesUtil;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  检查版本
 * - @Time:  2018/10/15
 * - @Emaill:  380948730@qq.com
 */
public class VersionDialog extends BaseButtonDialog {

    private String content;
    private String version;

    public VersionDialog(Activity context, String version, String content) {
        super(context);
        this.content = content;
        this.version = version;
    }


    @Override
    protected View setContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_version_resume, null);

        return contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.x328));
        setTitleText(ResourcesUtil.getString(R.string.mine_app_update));

        TextView contentTv = contentView.findViewById(R.id.update_dialog_content);
        TextView oldTv = contentView.findViewById(R.id.version_old_tv);
        TextView newTv = contentView.findViewById(R.id.version_new_tv);
        contentTv.setText(content);

        oldTv.setText(ResourcesUtil.getString(R.string.mine_old_version) + AppUpdateVersionCheckUtil.getVersion());
        newTv.setText(ResourcesUtil.getString(R.string.mine_old_version) + version);
    }

    @Override
    protected void initContentView(View contentView) {


    }

}
