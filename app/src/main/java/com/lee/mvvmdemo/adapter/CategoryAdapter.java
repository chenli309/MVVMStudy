package com.lee.mvvmdemo.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lee.mvvmdemo.R;
import com.lee.mvvmdemo.entity.CategoryResult.ResultsBean;
import com.lee.mvvmdemo.utils.LeeDateUtil;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerAdapterManager.LeeRecyclerAdapter<ResultsBean> {
    public CategoryAdapter() {
        super(R.layout.item_home_adapter_layout, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultsBean item) {

        if (!ObjectUtils.isEmpty(item.images)) {
            ImageView imageView = helper.getView(R.id.iv_picture);
            Glide.with(mContext).load(item.images.get(0) + "?imageView2/0/w/400").centerCrop().into(imageView);
            helper.setGone(R.id.iv_picture, true);
        } else {
            helper.setGone(R.id.iv_picture, false);
        }

        helper.setText(R.id.tv_title, item.desc == null ? "unknown" : item.desc)
                .setText(R.id.tv_author, item.who == null ? "unknown" : item.who)
                .setText(R.id.tv_time, LeeDateUtil.dateFormat(item.publishedAt))
                .addOnClickListener(R.id.item_root_layout);
    }
}

