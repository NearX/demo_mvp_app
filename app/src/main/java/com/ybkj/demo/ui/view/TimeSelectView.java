package com.ybkj.demo.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ybkj.demo.R;
import com.ybkj.demo.utils.DateUtil;
import com.ybkj.demo.utils.ResourcesUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 */
public class TimeSelectView {
    private static boolean[] timeState1 = new boolean[]{true, true, true, false, false, false};
    private static boolean[] timeState2 = new boolean[]{true, true, true, true, true, true};


    public static TimePickerView getTimePicker(Context context, TimerListener listener, int type) {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(0);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(DateUtil.getCurrentTimeMillis());
        boolean[] tiemType = null;
        switch (type) {
            case 1:
                tiemType = timeState1;
                break;
            case 2:
                tiemType = timeState2;
                break;
        }

        //时间选择器 ，自定义布局
        return new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                listener.onTimeSelect(date, v);
            }
        })

                /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                 .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                 .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                 .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                 .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                 .setSubmitColor(Color.WHITE)
                 .setCancelColor(Color.WHITE)*/
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onConform();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onCancel();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(tiemType)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(ResourcesUtil.getColor(R.color.color_blue_1))
                .build();
    }


    public interface TimerListener {
        void onTimeSelect(Date date, View v);

        void onCancel();

        void onConform();
    }
}
