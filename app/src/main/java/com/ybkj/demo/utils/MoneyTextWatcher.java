package com.ybkj.demo.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  设置输入框的监听
 * <p>
 * 默认限制小数点后2位  可传入指定数值
 * - @Time:  2018/11/20
 * - @Emaill:  380948730@qq.com
 */
public class MoneyTextWatcher implements TextWatcher {
    private EditText editText;
    private int digits = 2;

    public MoneyTextWatcher(EditText et) {
        editText = et;
    }

    public MoneyTextWatcher setDigits(int d) {
        digits = d;
        return this;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 2 + 1);
                editText.setText(s);
                editText.setSelection(s.length());
                //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        } //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
