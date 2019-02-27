package com.ybkj.demo.module.changeurl;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseActivity;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.utils.SPHelper;
import com.ybkj.demo.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeUrlActivity extends BaseActivity {
    public static String CHANGE_URL_KEY = "changgeUrlKey";
    @BindView(R.id.url_old_tv)
    TextView oldTv;
    @BindView(R.id.url_now_tv)
    TextView nowTv;
    @BindView(R.id.url_change_et)
    EditText changeEt;
    @BindView(R.id.url_change_btn)
    Button changeBtn;
    @BindView(R.id.url_change_old_btn)
    Button changeOldBtn;


    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_url;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        oldTv.setText("原始URL： " + Constants.DEFAULT_BASE_URL);
        nowTv.setText("正在使用的URL：  " + SPHelper.getInstance().getString(CHANGE_URL_KEY));
    }

    @OnClick({R.id.url_change_btn, R.id.url_change_old_btn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.url_change_btn: //修改URL
                String url = changeEt.getText().toString().trim();
                if (StringUtil.isNull(url)) {
                    toast("不能为空");
                    return;
                }
                SPHelper.getInstance().set(CHANGE_URL_KEY, url);
                toast("退出应用，并重启应用后才能生效");
                finish();
                break;
            case R.id.url_change_old_btn:
                SPHelper.getInstance().set(CHANGE_URL_KEY, Constants.DEFAULT_BASE_URL);
                toast("退出应用，并重启应用后才能生效");
                finish();
                break;
        }
    }
}
