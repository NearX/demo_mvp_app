package com.ybkj.demo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * author：rongkui.xiao --2018/6/1
 * email：dovexiaoen@163.com
 * description:
 */

public class FullGridView extends GridView {
    public FullGridView(Context context) {
        super(context);
    }

    public FullGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
//        setNestedScrollingEnabled(false);
    }
}
