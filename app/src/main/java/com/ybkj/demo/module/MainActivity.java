package com.ybkj.demo.module;

import android.view.KeyEvent;
import android.view.View;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.manager.ActivityManager;

import butterknife.OnClick;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  首页
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements IMainAtView {

    //点击回退的时间
    private long recodeTime = 0;

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


}
