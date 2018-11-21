package com.ybkj.demo.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.ui.adapter.base.XBaseAdapter;
import com.ybkj.demo.ui.adapter.base.XBaseViewHolder;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  BottomSelectAdapter
 * - @Time:  2018/10/23
 * - @Emaill:  380948730@qq.com
 */
public class BottomSelectAdapter extends XBaseAdapter<String> {
    public BottomSelectAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recycle_item_bottom_select;
    }

    @Override
    protected void convert(XBaseViewHolder helper, String item) {
        TextView name = helper.getView(R.id.deliver_select_item_name);
        TextView id = helper.getView(R.id.deliver_select_item_id);

        name.setText(item);
//        name.setTextColor(ResourcesUtil.getColor(item.isSelect() ? R.color.color_black_1 : R.color.color_gray_2));
    }
}
