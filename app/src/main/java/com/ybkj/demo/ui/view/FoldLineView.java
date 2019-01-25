package com.ybkj.demo.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ybkj.demo.R;
import com.ybkj.demo.bean.request.ListData;
import com.ybkj.demo.utils.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ywh
 * date : 2019/1/25 9:10
 * description :自定义view 折线图
 */
public class FoldLineView extends View {

    private int mWidth;//获取屏幕的宽度
    private int mHeight;//获取屏幕的高度
    private Paint xPaint;//x轴坐标线
    private Paint yPaint;//y轴坐标线
    private Paint pointPaint;//设置点的样式
    private Paint foldPaint;//绘制点和点之间的连线
    private float mBrokenLineLeft = 40;//边框左边距
    private float mBrokenLineTop = 40;//边框上边距
    private float mBrokenLineBottom = 40;//边框下边距
    private float mBrokenLinerRight = 20;//边框右边距
    private int[] xValueText = new int[]{0, 10, 20, 30, 40, 50, 60};//x轴对应的value值
    private int[] yValueText = new int[]{60, 50, 40, 30, 20, 10, 0};//y轴对应的value值
    private float mNeedDrawHeight;//需要绘制的高度
    private float mNeedDrawWidth;//需要绘制的宽度
    private float xTextPadding = 20;//x轴文字距离线的距离

    /**
     * 数据值
     */
    private List<ListData> listData = new ArrayList<>();


    /**
     * 图表的最大值
     */
    private int xMaxValue = 60;
    private int yMaxValue = 60;
    private float radius = 5;//半径

    public FoldLineView(Context context) {
        super(context);
    }

    public FoldLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    private void initData() {
        listData.add(new ListData(0, 15));
        listData.add(new ListData(10, 15));
        listData.add(new ListData(20, 15));
        listData.add(new ListData(30, 45));
        listData.add(new ListData(35, 55));
        listData.add(new ListData(45, 15));
        listData.add(new ListData(55, 15));
    }


    /**
     * 初始化一些基本操作
     */
    private void initPaint() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        xPaint = new Paint();
        xPaint.setColor(ResourcesUtil.getColor(R.color.color_gray_1));
        xPaint.setStyle(Paint.Style.STROKE);
        xPaint.setStrokeWidth(2);
        xPaint.setTextAlign(Paint.Align.RIGHT);
        xPaint.setTextSize(20);

        yPaint = new Paint();
        yPaint.setColor(ResourcesUtil.getColor(R.color.color_gray_1));
        yPaint.setStyle(Paint.Style.STROKE);
        yPaint.setStrokeWidth(2);
        yPaint.setTextAlign(Paint.Align.RIGHT);
        yPaint.setTextSize(20);

        pointPaint = new Paint();
        pointPaint.setColor(ResourcesUtil.getColor(R.color.color_orange_1));
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(10);

        foldPaint = new Paint();
        foldPaint.setStrokeWidth(2);
        foldPaint.setColor(ResourcesUtil.getColor(R.color.color_orange_1));
        foldPaint.setStyle(Paint.Style.STROKE);

    }

    public FoldLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        mWidth = width;
        mHeight = height;
        initNeedDrawWidthAndHeight();
    }

    /**
     * 实际view绘制的宽度和高度
     */
    private void initNeedDrawWidthAndHeight() {
        mNeedDrawWidth = mWidth - mBrokenLineLeft - mBrokenLinerRight;
        mNeedDrawHeight = mHeight - mBrokenLineTop - mBrokenLineBottom;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**绘制边框线和边框文本*/
        drawBorderLineAndText(canvas);
        /**绘制小圆点*/
        DrawPointCircle(canvas);

        /**根据数据绘制线*/
        DrawBrokenLine(canvas);
    }


    private void drawBorderLineAndText(Canvas canvas) {
        /**绘制边框竖线*/
        canvas.drawLine(mBrokenLineLeft, mBrokenLineTop - 10, mBrokenLineLeft, mHeight - mBrokenLineBottom, yPaint);
        /**绘制边框横线*/
        canvas.drawLine(mBrokenLineLeft, mHeight - mBrokenLineBottom, mWidth, mHeight - mBrokenLineBottom, xPaint);
        /**绘制边框的y轴分割线和文本*/
        float yAverageHeight = mNeedDrawHeight / (yValueText.length - 1);
        for (int i = 0; i < yValueText.length; i++) {
            /**根据i去计算每一段的高度从而循环绘制*/
            float iHeight = yAverageHeight * i;
            canvas.drawLine(mBrokenLineLeft, iHeight + mBrokenLineTop, mWidth - mBrokenLinerRight, iHeight + mBrokenLineTop, yPaint);
            /**在轴不绘制（0,iHeight + mBrokenLineTop-）字体*/
            if (i != yValueText.length - 1) {
                canvas.drawText(String.valueOf(yValueText[i]), mBrokenLineLeft - 5, iHeight + mBrokenLineTop, yPaint);
            }
        }


        /**绘制x轴边框的轴文本*/
        float xAverageWidth = mNeedDrawWidth / (xValueText.length - 1);
        for (int i = 0; i < xValueText.length; i++) {
            /**根据i去计算每一段的高度从而循环绘制*/
            float iWidth = xAverageWidth * i;
            canvas.drawLine(mBrokenLineLeft + iWidth, mHeight - mBrokenLineBottom, mBrokenLineLeft + iWidth, mBrokenLineTop, xPaint);
            canvas.drawText(String.valueOf(xValueText[i]), mBrokenLineLeft + iWidth + 5, mHeight - mBrokenLineBottom + xTextPadding, xPaint);
        }
    }

    private void DrawPointCircle(Canvas canvas) {
        /**根据给的数值去计算相应的x,y所在位置*/
        Point[] points = getPoints(mNeedDrawHeight, mNeedDrawWidth, xMaxValue, yMaxValue, mBrokenLineLeft, mBrokenLineTop);
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            /**
             * drawCircle(float cx, float cy, float radius, Paint paint)
             * cx 中间x坐标
             * xy 中间y坐标
             * radius 圆的半径
             * paint 绘制圆的画笔
             * */
            canvas.drawCircle(point.x, point.y, radius, pointPaint);

        }
    }

    /**
     * 绘制点和点之间的联系
     *
     * @param canvas
     */
    private void DrawBrokenLine(Canvas canvas) {
        Path path = new Path();
        Point[] points = getPoints(mNeedDrawHeight, mNeedDrawWidth, xMaxValue, yMaxValue, mBrokenLineLeft, mBrokenLineTop);
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }

        }
        canvas.drawPath(path, foldPaint);
        path.close();//闭环
    }

    /**
     * 根据值计算在该值的 x，y坐标
     *
     * @param height 图形的高度
     * @param width  图形的宽度
     * @param xMax   x轴最大值
     * @param yMax   y轴最大值
     * @param left
     * @param top
     * @return
     */
    private Point[] getPoints(float height, float width, int xMax, int yMax, float left, float top) {
        Point[] points = new Point[listData.size()];
        for (int i = 0; i < listData.size(); i++) {
            float yValue = listData.get(i).getY();
            float xValue = listData.get(i).getX();
            //计算每点高度所对应的值
            double yMean = (double) yMax / height;
            //计算每点宽度所对应的值
            double xMean = (double) xMax / width;
            //获取要绘制的宽度
            float drawWidth = (float) (xValue / xMean);
            //获取要绘制的高度
            float drawHeight = (float) (yValue / yMean);
            int pointY = (int) (height + top - drawHeight);
            int pointX = (int) (drawWidth + left);
            Point point = new Point(pointX, pointY);
            points[i] = point;
        }
        return points;
    }

}
