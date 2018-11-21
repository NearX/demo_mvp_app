package com.ybkj.demo.module.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.bean.response.MineDataRes;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.module.mine.presenter.PersonalPresenter;
import com.ybkj.demo.module.mine.view.PersonalView;
import com.ybkj.demo.utils.ResourcesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class PersonalActivity extends BaseMvpActivity<PersonalPresenter> implements PersonalView {

    //账号
    @BindView(R.id.mine_account_tv)
    TextView mineAccountTv;
    //编辑
    @BindView(R.id.edit_iv)
    ImageView editIv;
    //姓名
    @BindView(R.id.name_tv)
    TextView nameTv;
    //头像
    @BindView(R.id.head_iv)
    ImageView headIv;
    //个人信息
    @BindView(R.id.personal_message_tv)
    TextView personalMessageTv;
    //员工编号
    @BindView(R.id.user_code_tv)
    TextView userCodeTv;
    @BindView(R.id.department_tv)
    TextView departmentTv;
    @BindView(R.id.position_tv)
    TextView positionTv;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {
        setTitleText(ResourcesUtil.getString(R.string.mine));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.getMineData();
    }


    @OnClick({R.id.personal_message_tv, R.id.check_version_tv, R.id.edit_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //检查版本
            case R.id.check_version_tv:
                ActivityManager.gotoActivity(mContext, CheckVersionActivity.class);
                break;
            //修改账号
            case R.id.edit_iv:
                startActivity(new Intent(mContext, ModifyAccountActivity.class));

                break;
        }
    }


    @Override
    public void setMinData(MineDataRes myInfo) {
        if (myInfo != null && myInfo.getMyInfoVO() != null) {
            mineAccountTv.setText(myInfo.getMyInfoVO().getUserAccount());
            nameTv.setText(myInfo.getMyInfoVO().getUserName());
            userCodeTv.setText(myInfo.getMyInfoVO().getUserCode());
            departmentTv.setText(myInfo.getMyInfoVO().getDepartmentName());
            positionTv.setText(myInfo.getMyInfoVO().getPostName());
        }
    }

}
