package com.ybkj.demo.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ybkj.demo.R;

/**
 * author : ywh
 * date : 2019/1/23 9:45
 * description :可清除的输入框
 */
public class ClearEditText extends EditText
        implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearIcon;

    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener mOnFocusChangeListener) {
        this.mOnFocusChangeListener = mOnFocusChangeListener;
    }

    public ClearEditText(Context context) {
        super(context);
        initialize(context);
    }


    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * 设置right 图标
     *
     * @param context
     */
    private void initialize(Context context) {
        final Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.icon_input_del);

        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable); //Wrap the drawable so that it can be tinted pre Lollipop
        //DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        mClearIcon = wrappedDrawable;
        mClearIcon.setBounds(0, 0, mClearIcon.getIntrinsicWidth(), mClearIcon.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
        ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(context, R.color.color_black_1));
    }

    private void setClearIconVisible(boolean visible) {
        if (mClearIcon.isVisible() == visible) return;

        mClearIcon.setVisible(visible, false);
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                visible ? mClearIcon : null,
                compoundDrawables[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (isFocused()) {
            setClearIconVisible(charSequence.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * @param view
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus && getText() != null) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (mClearIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearIcon.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setText("");
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
    }
}
