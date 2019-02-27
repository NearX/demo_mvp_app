package com.ybkj.demo.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ybkj.demo.R;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.manager.SystemManager;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.ResourcesUtil;
import com.ybkj.demo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  最基础Activity
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    public List<Disposable> disposableList = new ArrayList<>();

    protected Activity mContext;
    private Unbinder unbinder;
    private View mNetErrorView;
    //头部标题
    private TextView title;
    //头部下划线
    private View bottomLine;
    //头部右侧文字
    private TextView rightText;
    //头部右侧图片
    private ImageView rightImg;
    //返回按钮
    private View backView;
    //无数据显示的view
    private View mNetEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityManager.getInstance().addActivity(this);
        if (isImmersiveStatusBar())
            SystemManager.setStatusBar(this, ResourcesUtil.getColor(immersiveStatusBarColor()));

        //绑定控件注解
        unbinder = ButterKnife.bind(this);
        if (savedInstanceState != null) {
            initSavedInstanceState(savedInstanceState);
        }
        if (getIntent() != null) {
            initIntentData(getIntent());
        }
        initHeader();
        initView();
        initTitle();
        initData();
    }


    protected void initHeader() {
        backView = findViewById(R.id.back_iv);
        title = findViewById(R.id.title);
        bottomLine = findViewById(R.id.head_bottom_line);
        rightText = findViewById(R.id.head_right_text);
        rightImg = findViewById(R.id.head_right_img);
        if (backView != null) {
            backView.setOnClickListener(v -> ActivityManager.getInstance().removeCurrent());
        }

        if (rightText != null) {
            rightText.setOnClickListener(v -> onRightTextClick());
        }
        if (rightImg != null) {
            rightImg.setOnClickListener(v -> onRightImgClick());
        }
    }


    /**
     * 右边文字点击
     */
    public void onRightTextClick() {

    }

    /**
     * 右边图片点击
     */
    public void onRightImgClick() {

    }

    /**
     * 是否使用沉浸式状态栏，默认不使用
     */
    public boolean isImmersiveStatusBar() {
        return false;
    }

    /**
     * 沉浸式状态栏颜色，默认蓝色
     */
    protected int immersiveStatusBarColor() {
        return R.color.status_bar_color;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        //清空任务栈
        if (disposableList != null) {
            for (Disposable disposable : disposableList) {
                disposable.dispose();
            }
            disposableList.clear();
        }
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 初始化Activity异常销毁保存的数据
     */
    public void initSavedInstanceState(Bundle bundle) {

    }

    /**
     * 初始化intent传递的数据
     */
    public void initIntentData(Intent intent) {

    }


    protected abstract void initTitle();

    /**
     * 获取布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据,在initView之后执行
     */
    protected abstract void initData();


    protected void toast(int res) {
        toast(mContext.getResources().getText(res));
    }

    protected void toast(CharSequence res) {
        ToastUtil.showShort(res);
    }

    /**
     * 设置网络数据加载失败界面 true则显示错误， false 显示数据
     *
     * @param view   被错误界面替换
     * @param isShow 是否显示错误界面
     */
    protected void showNetErrorView(ViewGroup view, boolean isShow) {
        if (mNetErrorView == null) {
            mNetErrorView = View.inflate(mContext, getNetErrorLayoutRes(), null);
            Button button = mNetErrorView.findViewById(R.id.net_error_btn);
            button.setOnClickListener(v -> tryData(view.getId()));
        }
        if (isShow) {
            view.setVisibility(View.GONE);
            ViewGroup showViewParent = (ViewGroup) view.getParent();
            int indexOfChild = showViewParent.indexOfChild(view);
            int indexOfChildError = showViewParent.indexOfChild(mNetErrorView);
            if (indexOfChildError < 0)//表示当前错误界面不存在
                showViewParent.addView(mNetErrorView, indexOfChild);
        } else {
            view.setVisibility(View.VISIBLE);
            mNetErrorView.setVisibility(View.GONE);
        }

    }


    /**
     * 设置当前界面无数据可显示 true则显示错误， false 显示数据
     *
     * @param view   被错误界面替换
     * @param isShow 是否显示错误界面
     */
    protected void showEmptyView(ViewGroup view, boolean isShow) {
        if (mNetEmptyView == null) {
            mNetEmptyView = View.inflate(mContext, getNetEmptyLayoutRes(), null);
            mNetEmptyView.setOnClickListener(v -> tryData(view.getId()));
        }
        if (isShow) {
            view.setVisibility(View.GONE);
            ViewGroup showViewParent = (ViewGroup) view.getParent();
            int indexOfChild = showViewParent.indexOfChild(view);
            int indexOfChildError = showViewParent.indexOfChild(mNetEmptyView);
            if (indexOfChildError < 0)//表示当前错误界面不存在
                showViewParent.addView(mNetEmptyView, indexOfChild);
        } else {
            view.setVisibility(View.VISIBLE);
            mNetEmptyView.setVisibility(View.GONE);
        }
    }


    /**
     * 设置当前页面RecycleView加载不出数据的时候显示的view
     *
     * @param recyclerView
     */
    protected void showNetRecycleEmptyView(RecyclerView recyclerView) {
        if (mNetEmptyView == null) {
            mNetEmptyView = View.inflate(mContext, getNetEmptyLayoutRes(), null);
        }

        RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
        if (recyclerViewAdapter != null && recyclerViewAdapter instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) recyclerViewAdapter).setEmptyView(mNetEmptyView);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }


    //点击错误界面时触发刷新
    protected void tryData(int id) {
        LogUtil.i("点击刷新");
    }

    /*可以自定义错误界面*/
    protected int getNetErrorLayoutRes() {
        return R.layout.net_load_error;
    }


    /**
     * 自定义无数据界面
     *
     * @return
     */
    protected int getNetEmptyLayoutRes() {
        return R.layout.empty_view;
    }

    @Override
    public void onClick(View v) {
    }


    /**
     * 设置标题文字
     *
     * @param titleText
     */
    protected void setTitleText(String titleText) {
        if (title != null && titleText != null) {
            title.setText(titleText);
        }
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    protected void setRightText(String text) {
        if (rightText != null && text != null) {
            rightText.setVisibility(View.VISIBLE);
            rightText.setText(text);
        }
    }

    /**
     * 设置右边图片
     *
     * @param drawable
     */
    protected void setRightImg(Drawable drawable) {
        if (rightImg != null) {
            rightImg.setVisibility(View.VISIBLE);
            rightImg.setImageDrawable(drawable);
        }
    }

    /**
     * 设置右边文字是否显示
     */
    protected void setRightTextVisible(boolean isVisible) {
        if (rightText != null) {
            rightText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置底部线是否显示
     *
     * @param visibility
     */
    protected void setBottomLineVisibility(int visibility) {
        if (bottomLine != null) {
            bottomLine.setVisibility(visibility);
        }
    }

    /**
     * 设置返回按钮是否显示
     *
     * @param visibility
     */
    protected void setBackImgVisibility(int visibility) {
        if (backView != null) {
            backView.setVisibility(visibility);
        }
    }


}
