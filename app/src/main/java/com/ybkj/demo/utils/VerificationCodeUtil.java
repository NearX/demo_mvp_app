package com.ybkj.demo.utils;

import android.widget.TextView;

import com.ybkj.demo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  验证码倒计时工具类
 * - @Time:  2018/7/27
 * - @Emaill:  380948730@qq.com
 */

public class VerificationCodeUtil {
    //倒计时时间
    private final int splashTotalCountdownTime = 60;
    private Disposable disposable;

    public VerificationCodeUtil() {

    }

    /**
     * 验证码倒计时
     */
    public void codeCountDown(TextView verificationCode) {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).map(aLong -> splashTotalCountdownTime - aLong.intValue()).take
                (splashTotalCountdownTime + 1).subscribe(integer -> {
            if (integer == 0) {
                verificationCode.setText(ResourcesUtil.getString(R.string.verification_code_get));
                verificationCode.setEnabled(true);
                verificationCode.setAlpha(1);
            } else {
                verificationCode.setText(String.format(ResourcesUtil.getString(R.string.verification_code_get_sub),
                        integer));
                verificationCode.setEnabled(false);
                verificationCode.setTextColor(ResourcesUtil.getColor(R.color.color_green_1));
//                verificationCode.setAlpha(0.5f);
            }
        });
    }

    public void cancel() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
