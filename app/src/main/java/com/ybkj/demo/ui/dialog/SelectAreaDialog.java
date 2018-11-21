package com.ybkj.demo.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.demo.R;
import com.ybkj.demo.bean.AreaModel;
import com.ybkj.demo.common.PhoneAreaConfig;
import com.ybkj.demo.ui.adapter.AreaSelectAdapter;
import com.ybkj.demo.ui.view.recyclerview.XRecyclerView;
import com.ybkj.demo.utils.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  选择区号弹出框
 * - @Time:  2018/8/1
 * - @Emaill:  380948730@qq.com
 */
public class SelectAreaDialog extends BaseDialog implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public SelectAreaDialog(Activity context) {
        super(context, R.style.common_dialog, R.layout.dialog_area_select);
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setAlertDialogGravity(Gravity.BOTTOM);
        initView();
        setAlertDialogHeight((int) ResourcesUtil.getDimen(R.dimen.y500));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_dialog_select_close: //关闭选择框
                dismiss();
                break;
        }
    }

    protected void initView() {
        TextView selectTittle = findViewById(R.id.change_dialog_select_tittle);//选择界面标题
        ImageView selectClose = findViewById(R.id.change_dialog_select_close);//选择界面关闭
        XRecyclerView recycle = findViewById(R.id.area_select_recycle);//区域列表
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        List<AreaModel> areaList = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : phoneAreaMap.entrySet()) {
            areaList.add(new AreaModel(stringStringEntry.getKey(), stringStringEntry.getValue()));
        }
        selectClose.setOnClickListener(this);

        AreaSelectAdapter adapter = new AreaSelectAdapter(mContext);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onItemClickListener.onItemClick(areaList.get(position).getId(), areaList.get(position).getName());
                dismiss();
            }
        });

        adapter.setNewData(areaList);
        recycle.setAdapter(adapter);

    }

    public interface OnItemClickListener {
        void onItemClick(String areaId, String areaName);
    }
}
