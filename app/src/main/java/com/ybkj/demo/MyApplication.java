package com.ybkj.demo;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  ThinkerApplication
 * - @Time:  2018/7/26
 * - @Emaill:  380948730@qq.com
 */
public class MyApplication extends TinkerApplication {

    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.ybkj.demo.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
