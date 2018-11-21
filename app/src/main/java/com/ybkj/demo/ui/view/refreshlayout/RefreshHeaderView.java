package com.ybkj.demo.ui.view.refreshlayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ybkj.demo.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author neo.duan
 * @date 2018/1/5 12:05
 * @desc 下拉刷新Header View
 */
public class RefreshHeaderView extends FrameLayout implements PtrUIHandler {

//    private AnimationDrawable animationRefresh;

    Animation mRotateAnim;
    private ImageView mIvOut;
    private TextView mTvState;

    public RefreshHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_pull_load_view, this);
        mIvOut = findViewById(R.id.iv_pull_refresh_out);
        mTvState = findViewById(R.id.tv_pull_refresh_out_state);
//        mIvPerson.setImageResource(R.drawable.refreshing_anim);

        // 通过ImageView对象拿到背景显示的AnimationDrawable
//        animationRefresh = (AnimationDrawable) mIvPerson.getDrawable();
    }

    /**
     * 重置
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
//        animationRefresh.stop();
        cancelRotateAnim();
    }

    /**
     * 准备刷新
     *
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            mTvState.setText("下拉加载");
        }
    }

    /**
     * 刷新中
     *
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
//        animationRefresh.start();
        mTvState.setText("加载中");
        startRotateAnim();
    }

    /**
     * 刷新完成
     *
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
//        animationRefresh.start();
        mTvState.setText("加载成功");
        cancelRotateAnim();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();

        if (currentPos < mOffsetToRefresh) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mTvState.setText("下拉刷新");

                float rate = (float) currentPos / mOffsetToRefresh;
                mIvOut.setRotation(rate * 360);
            }
        } else if (currentPos > mOffsetToRefresh) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mTvState.setText("释放加载");
            }
        }
    }


    /**
     * 开启动画
     */
    private void startRotateAnim() {
        if (mRotateAnim == null) {
            mRotateAnim = new RotateAnimation(0f, 360f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            mRotateAnim.setFillAfter(false); // 设置保持动画最后的状态
            mRotateAnim.setRepeatCount(-1);
            mRotateAnim.setDuration(800); // 设置动画时间
            mRotateAnim.setInterpolator(new LinearInterpolator()); // 设置插入器
            mIvOut.startAnimation(mRotateAnim);
        }
    }

    /**
     * 取消动画
     */
    private void cancelRotateAnim() {
        if (mRotateAnim != null) {
            mIvOut.clearAnimation();
            mRotateAnim.cancel();
            mRotateAnim = null;
        }
    }
}
