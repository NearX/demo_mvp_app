package com.ybkj.demo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ybkj.demo.R;


/**
 * Created by Yun on 2018/1/10.
 * 最大高度限制ListView
 *
 * @author Yun
 */
public class MaxHeightLinearLayout extends LinearLayout {

    private final int maxHeight;

    public MaxHeightLinearLayout(Context context) {
        this(context, null);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaxHeightLinearLayout);
            maxHeight = a.getDimensionPixelSize(R.styleable.MaxHeightLinearLayout_maxHeight, Integer.MAX_VALUE);
            a.recycle();
        } else {
            maxHeight = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (maxHeight > 0 && maxHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
