package com.ybkj.demo.ui.view.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.ybkj.demo.ui.adapter.base.XBaseAdapter;


/**
 * @author neo.duan
 * @date 2018/1/5 12:05
 * @desc RecyclerView ListView分割线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    private int mOrientation;

    public DividerItemDecoration(Context context, float divider) {
        this(context, VERTICAL_LIST, divider, ContextCompat.getColor(context, android.R.color.transparent));
    }

    public DividerItemDecoration(Context context, int orientation, float divider) {
        this(context, orientation, divider, ContextCompat.getColor(context, android.R.color.transparent));
    }

    public DividerItemDecoration(Context context, int orientation, float divider, String color) {
        this(context, orientation, divider, Color.parseColor(color));
    }

    public DividerItemDecoration(Context context, int orientation, float divider, int color) {
        mDivider = getDefaultDrawable(context, divider, color);
        setOrientation(orientation);
    }

    public DividerItemDecoration(Context context, int orientation, Drawable drawable) {
        mDivider = drawable;
        setOrientation(orientation);
    }

    private static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    private Drawable getDefaultDrawable(Context context, float divider, int color) {
        int newDivider = (int) dpToPx(context, divider);

        ShapeDrawable drawable = new ShapeDrawable();
        drawable.setIntrinsicWidth(newDivider);
        drawable.setIntrinsicHeight(newDivider);
        drawable.getPaint().setColor(color);

        return drawable;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildLayoutPosition(view);

        //如果是header或footer就不需要
        RecyclerView.Adapter adapter = parent.getAdapter();
        switch (adapter.getItemViewType(itemPosition)) {
            case XBaseAdapter.HEADER_VIEW:
            case XBaseAdapter.FOOTER_VIEW:
                return;
            default:
                break;
        }

        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
