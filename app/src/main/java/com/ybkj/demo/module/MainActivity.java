package com.ybkj.demo.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.ui.dialog.PictureSelectDialog;
import com.ybkj.demo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  首页
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements IMainAtView {


    @BindView(R.id.image_select_button)
    Button imageSelectButton;
    //点击回退的时间
    private long recodeTime = 0;
    //图片选择弹框
    private PictureSelectDialog pictureSelectDialog;

    @Override
    protected void initTitle() {
        setBottomLineVisibility(View.GONE);
        //隐藏返回键
        setBackImgVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onRightTextClick() {
        super.onRightTextClick();
    }

    @Override
    protected void initView() {
        imageSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictureSelectDialog==null)
                    initPictureSelectDialog();
                pictureSelectDialog.show();

            }
        });
    }

    @Override
    protected void initData() {
    }

    /**
     * 连续点击2次返回键才能退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - recodeTime > 2000) {
                toast(getResources().getString(R.string.click_exit));
                recodeTime = System.currentTimeMillis();
                return true;
            } else {
                //退出程序
                finish();
                ActivityManager.exit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({})
    public void onViewClicked(View view) {

    }


    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }


    private void initPictureSelectDialog(){
        pictureSelectDialog=new PictureSelectDialog(mContext);
        pictureSelectDialog.setCrop(false);
        pictureSelectDialog.setOutputSize(1000,1000);
        pictureSelectDialog.setOnSelectSuccessListener(new PictureSelectDialog.OnSelectSuccessListener() {
            @Override
            public void onBytesSuccess(byte[] bytes, int tag) {
                ToastUtil.showShort("bytes.length="+bytes.length);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (pictureSelectDialog!=null)
            pictureSelectDialog.onActivityResult(requestCode,resultCode,data);
    }
}
