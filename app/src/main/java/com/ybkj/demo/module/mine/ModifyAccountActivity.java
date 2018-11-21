package com.ybkj.demo.module.mine;

import android.widget.Button;
import android.widget.EditText;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.module.mine.presenter.ModifyAccountPresenter;
import com.ybkj.demo.module.mine.view.ModifyAccountView;
import com.ybkj.demo.utils.ResourcesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改账号
 */
public class ModifyAccountActivity extends BaseMvpActivity<ModifyAccountPresenter> implements ModifyAccountView {

    @BindView(R.id.account_et)
    EditText accountEt;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected void injectPresenter() {

        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {
        setTitleText(ResourcesUtil.getString(R.string.modify_account));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_account;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {
        String account = accountEt.getText().toString().trim();
        presenter.modifyAccount(account);
    }

    @Override
    public void modifySuccess() {
        finish();
    }
}
