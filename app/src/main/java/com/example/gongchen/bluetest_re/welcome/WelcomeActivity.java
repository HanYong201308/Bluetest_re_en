package com.example.gongchen.bluetest_re.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gongchen.bluetest_re.MainActivity;
import com.example.gongchen.bluetest_re.R;
import com.example.gongchen.bluetest_re.base.BaseActivity;
import com.example.gongchen.bluetest_re.util.GsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class WelcomeActivity extends BaseActivity {

    BGABanner mBackgroundBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    WelcomeBean WelcomeBean;

    private void initView() {
        final List<View> views = new ArrayList<>();


        mBackgroundBanner = findViewById(R.id.banner_guide_background);

        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
        OkGo.<String>get("http://39.97.117.238:8080/loop/list")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        WelcomeBean = GsonUtils.GsonToBean(body, WelcomeBean.class);
                        List<com.example.gongchen.bluetest_re.welcome.WelcomeBean.ListBean> list = WelcomeBean.getList();
                        for (int i = 0; i < list.size(); i++) {
                            View view = getLayoutInflater().inflate(R.layout.item_image, null);
                            ImageView imageView = view.findViewById(R.id.item_images);
                            String[] tempUrl = list.get(i).getPhotourl().split(",");
                            byte[] decodedString = Base64.decode(tempUrl[1], Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView.setImageBitmap(decodedByte);
                            views.add(view);
                        }
                        mBackgroundBanner.setData(views);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}


