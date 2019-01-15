package com.ybkj.demo.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.GlideApp;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  图片放大弹出框
 */
public class ImgDialog extends Dialog {


    private ImageView img;
    private String url;
    private Context context;
    private ImageView imgBack;

    public ImgDialog(Context context, String url) {
        super(context, R.style.img_dialog);
        this.url = url;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_img);
        initView();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    private void initView() {
        img = findViewById(R.id.dialog_img);
        imgBack = findViewById(R.id.dialog_img_back);
        GlideApp.with(context).load(url).into(img);
        imgBack.setOnClickListener(v -> {
            cancel();
        });
        img.setOnClickListener(v -> {
            cancel();
        });
    }
}