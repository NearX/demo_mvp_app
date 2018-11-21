package com.ybkj.demo.module.mine;

import android.widget.EditText;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.module.mine.presenter.ModifyPsdPresenter;
import com.ybkj.demo.module.mine.view.ModifyPsdView;
import com.ybkj.demo.utils.ResourcesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ModifyPsdActivity extends BaseMvpActivity<ModifyPsdPresenter> implements ModifyPsdView {


    @BindView(R.id.new_password_et)
    EditText newPasswordEt;
    @BindView(R.id.again_password_et)
    EditText againPasswordEt;
    @BindView(R.id.old_password_et)
    EditText oldPasswordEt;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {
        setTitleText(ResourcesUtil.getString(R.string.modify_password));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_psd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.submit_btn)
    public void onViewClicked() {
        String oldPassword = oldPasswordEt.getText().toString().trim();
        String newPassword = newPasswordEt.getText().toString().trim();
        String repeatPassword = againPasswordEt.getText().toString().trim();
        presenter.modifyPsd(oldPassword, newPassword, repeatPassword);
    }

    @Override
    public void modifySuccess() {
        finish();
    }
}
