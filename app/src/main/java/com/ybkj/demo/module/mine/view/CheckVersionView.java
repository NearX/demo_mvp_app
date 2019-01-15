package com.ybkj.demo.module.mine.view;

import com.ybkj.demo.base.BaseView;
import com.ybkj.demo.bean.response.VersionRes;

public interface CheckVersionView extends BaseView {
    void LoadData(VersionRes.AppVersionBean res);
}
