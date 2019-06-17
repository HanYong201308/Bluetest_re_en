package com.example.gongchen.bluetest_re;

import android.app.Application;

import com.lzy.okgo.OkGo;

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
