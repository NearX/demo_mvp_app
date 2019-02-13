package com.ybkj.demo.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ybkj.demo.R;

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    private int mLineColor; //外边框颜色
    private int mBackgroundColor;//背景颜色
    private int mLineHeight; //外边框高度
    private int mRadius; //圆角
    private GradientDrawable mDrawable;
    private int mLeftTopRadius;//左上圆角
    private int mLeftBottomRadius;//左下角圆角
    private int mRightTopRadius;//右上角圆角
    private int mRightBottomRadius;//右下角圆角
    private GradientDrawable mBackgroundNormal;


    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void getAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.myTextView);
            mLineColor = mTypedArray.getColor(R.styleable.myTextView_line_color, Color.TRANSPARENT);
            mBackgroundColor = mTypedArray.getColor(R.styleable.myTextView_background_color, Color.TRANSPARENT);
            mLineHeight = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_line_height, 0);
            mRadius = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_radius, 0);

            mLeftTopRadius = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_corner_radius_top_left, 0);
            mLeftBottomRadius = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_corner_radius_bottom_left, 0);
            mRightTopRadius = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_corner_radius_top_right, 0);
            mRightBottomRadius = mTypedArray.getDimensionPixelOffset(R.styleable.myTextView_corner_radius_bottom_right, 0);
            mDrawable = createDrawable(mLineColor, mRadius, mBackgroundColor, mLineHeight);
            setBackgroundCompat(mDrawable);
        }
    }

    /**
     * 创建背景drawable
     *
     * @param mLineColor
     * @param mRadius
     * @param mBackgroundColor
     * @param mLineHeight
     * @return
     */
    private GradientDrawable createDrawable(int mLineColor, int mRadius, int mBackgroundColor, int mLineHeight) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(mRadius);
        gradientDrawable.setColor(mBackgroundColor);
        gradientDrawable.setStroke(mLineHeight, mLineColor);

        return gradientDrawable;
    }


    /**
     * 设置背景颜色
     *
     * @param drawable
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(@Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setLineColor(int color) {
        this.mLineColor = color;
        setTextStyle(color, mRadius, mBackgroundColor, mLineHeight);
    }

    public void setAllRadius(int radius) {
        this.mRadius = radius;
        setTextStyle(mLineColor, radius, mBackgroundColor, mLineHeight);
    }

    public void setLineHeight(int height) {
        this.mLineHeight = height;
        setTextStyle(mLineColor, mRadius, mBackgroundColor, mLineHeight);
    }

    public void setBackgroundColorStyle(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        setTextStyle(mLineColor, mRadius, mBackgroundColor, mLineHeight);
    }


    /**
     * 设置style样式
     *
     * @param color
     * @param mRadius
     * @param mBackgroundColor
     * @param mLineHeight
     */
    private void setTextStyle(int color, int mRadius, int mBackgroundColor, int mLineHeight) {
        mDrawable = createDrawable(color, mRadius, mBackgroundColor, mLineHeight);
        setBackgroundCompat(mDrawable);
    }

    /**
     * 设置左上角圆角
     *
     * @param leftTopRadius
     */
    public void setLeftTopRadius(int leftTopRadius) {
        this.mLeftBottomRadius = leftTopRadius;

    }

    /**
     * 设置左下角圆角
     *
     * @param leftBottomRadius
     */
    public void setLeftBottomRadius(int leftBottomRadius) {
        this.mLeftBottomRadius = leftBottomRadius;
    }

    /**
     * 设置右上角圆角
     *
     * @param rightTopRadius
     */
    public void setRightTopRadius(int rightTopRadius) {
        this.mRightBottomRadius = rightTopRadius;
    }

    /**
     * 设置有下角圆角
     *
     * @param rightBottomRadius
     */
    public void setRightBottomRadius(int rightBottomRadius) {
        this.mRightBottomRadius = rightBottomRadius;
    }
}
