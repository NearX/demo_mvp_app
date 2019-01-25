package com.ybkj.demo.module.login.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.module.MainActivity;
import com.ybkj.demo.module.login.presenter.LoginPresenter;
import com.ybkj.demo.module.login.view.ILoginAtView;
import com.ybkj.demo.ui.dialog.SelectAreaDialog;
import com.ybkj.demo.ui.view.ClearEditText;
import com.ybkj.demo.ui.view.MyTextView;
import com.ybkj.demo.utils.ResourcesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoginAtView {

    //账号
    @BindView(R.id.login_account_et)
    ClearEditText accountEt;
    //区号
    @BindView(R.id.login_area_tv)
    TextView areaTv;
    //密码
    @BindView(R.id.login_pwd_et)
    EditText passwordEt;
    //登录按钮
    @BindView(R.id.login_btn)
    Button loginBtn;
    //忘记密码
    @BindView(R.id.login_forgot_tv)
    TextView loginForgotTv;
    @BindView(R.id.myText)
    MyTextView myText;
    private SelectAreaDialog selectAreaDialog;

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        myText.setLineColor(ResourcesUtil.getColor(R.color.color_blue_1));

        myText.setBackgroundColorStyle(ResourcesUtil.getColor(R.color.colorAccent));


        selectAreaDialog = new SelectAreaDialog(mContext);
        selectAreaDialog.setOnItemClickListener(new SelectAreaDialog.OnItemClickListener() {
            @Override
            public void onItemClick(String areaId, String areaName) {
                areaTv.setText(areaId);
                selectAreaDialog.dismiss();
            }
        });

      accountEt.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {
              return false;
          }
      });

        accountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    /**
     * 登录业务
     */
    @OnClick({R.id.login_btn, R.id.login_forgot_tv, R.id.login_area_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String account = accountEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();
                String area = areaTv.getText().toString().trim();
                presenter.login(area, account, password);
                ActivityManager.gotoActivity(mContext, MainActivity.class);
                break;
            case R.id.login_forgot_tv:
                break;
            case R.id.login_area_tv:
                selectAreaDialog.show();
                break;
        }
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }

    @Override
    public void gotoMainActivity() {
        ActivityManager.gotoActivity(mContext, MainActivity.class);
        finish();
    }

    /**
     * 初始密码123456
     */
    @Override
    public void gotoModifyPwdActivity() {
    }

}
