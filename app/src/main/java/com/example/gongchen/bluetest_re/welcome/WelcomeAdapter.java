package com.example.gongchen.bluetest_re.welcome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.gongchen.bluetest_re.R;

import java.util.List;

import cn.qqtheme.framework.util.LogUtils;

public class WelcomeAdapter extends BaseQuickAdapter<WelcomeBean.ListBean, BaseViewHolder> {
    private Context mContext;

    public WelcomeAdapter(int layoutResId, @Nullable List<WelcomeBean.ListBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WelcomeBean.ListBean item) {
        ImageView imageView = helper.getView(R.id.item_images);
        String[] tempUrl = item.getPhotourl().split(",");
        byte[] decodedString = Base64.decode(tempUrl[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Log.e("aaaa", "" + decodedByte);
        imageView.setImageBitmap(decodedByte);
    }
}
