package com.ybkj.demo.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ybkj.demo.R;


/**
 * Description 自定义带飞机的ProgressBar
 * Author Ren Xingzhi
 * Created on 2019/1/24.
 * Email 15384030400@163.com
 */
public class PlaneProgressBar extends View {
    private int progress = 0;//当前进度
    private int width;//view宽
    private int height;//view高

    private Paint progressPaint;//进度画笔
    private Paint bgPaint;//进度条背景画笔
    private Paint textPaint;//进度文字画笔
    private Paint textPaintSmall;//百分号画笔

    private Bitmap planeBitmap;
    private float textX;
    private float textY;
    private float percentTextX;
    private float progressX;

    private RectF bgRectF;
    private RectF progressRectF;

    public PlaneProgressBar(Context context) {
        super(context);
    }

    public PlaneProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL);//充满
        progressPaint.setColor(getResources().getColor(R.color.color_red_1));
        progressPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);//充满
        bgPaint.setColor(getResources().getColor(R.color.color_line2));
        bgPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.color_red_1));
        textPaint.setTextSize(58);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        textPaintSmall = new TextPaint();
        textPaintSmall.setColor(getResources().getColor(R.color.color_red_1));
        textPaintSmall.setTextSize(40);
        textPaintSmall.setFakeBoldText(true);
        textPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画进度文字
        canvas.drawText(String.valueOf(progress), textX, textY, textPaint);
        //画%
        canvas.drawText("%", percentTextX, textY, textPaintSmall);
        //画背景进度
        canvas.drawRoundRect(bgRectF, 4.3f, 4.3f, bgPaint);
        //画已有进度
        canvas.drawRoundRect(progressRectF, 4.3f, 4.3f, progressPaint);
        //画飞机
        canvas.drawBitmap(planeBitmap, progressX, textY + 10, progressPaint);


    }

    private void init() {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        planeBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.progress_plane)).getBitmap();
        progressX = width * progress / 100;
        textY = textPaint.getFontSpacing();
        if (progressX > width - planeBitmap.getWidth()) {
            progressX = width - planeBitmap.getWidth();
        }
        float lineY = (float) (textY + 10 + planeBitmap.getHeight() / 2 - 4.3);

        float progressWidth = textPaint.measureText(String.valueOf(progress));
        float percentWidth = textPaint.measureText("%");
        textX = progressX + progressWidth + percentWidth > width ? width - progressWidth - percentWidth : progressX;
        percentTextX = textX + progressWidth;
        bgRectF = new RectF(0, lineY, width, lineY + 8.6f);
        progressRectF = new RectF(0, lineY, progressX, lineY + 8.6f);

    }

    /**
     * 更新进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;

        progressX = width * progress / 100;
        textY = textPaint.getFontSpacing();
        if (progressX > width - planeBitmap.getWidth()) {
            progressX = width - planeBitmap.getWidth();
        }
        float lineY = (float) (textY + 10 + planeBitmap.getHeight() / 2 - 4.3);

        float progressWidth = textPaint.measureText(String.valueOf(progress));
        float percentWidth = textPaint.measureText("%");
        textX = progressX + progressWidth + percentWidth > width ? width - progressWidth - percentWidth : progressX;
        percentTextX = textX + progressWidth;
        bgRectF = new RectF(0, lineY, width, lineY + 8.6f);
        progressRectF = new RectF(0, lineY, progressX + 10, lineY + 8.6f);
        postInvalidate();
    }
}
