package com.example.gongchen.bluetest_re.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.gongchen.bluetest_re.R;
import com.example.gongchen.bluetest_re.util.MyActivityManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Author: Administrator
 * Time: 2019/6/3
 * Description:
 */
public class BaseActivity extends AppCompatActivity {

    public BaseActivity mBaseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBaseActivity = this;
        beforeActivityInTask();
        MyActivityManager.getInstance().addActivity(mBaseActivity);
        afterActivityInTask();
        getIntentData();
        findViews(savedInstanceState);
        initStateBar();
        initViews();
        addListeners();
        requestOnCreate();
    }
    /**
     * 获取界面传递数据
     */
    protected void getIntentData() {

    }
    protected void afterActivityInTask() {

    }

    protected void beforeActivityInTask() {
    }


    /**
     * 初始化沉浸式
     */
    private void initStateBar() {
        /*  setColorId();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            /* window.setStatusBarColor(color);*/
            window.setStatusBarColor(getColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            /*     tintManager.setNavigationBarTintEnabled(true);*/
            //设置系统栏设置颜色
            tintManager.setTintColor(getColor());
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(getColor());
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            /*tintManager.setNavigationBarTintResource(R.color.mask_tags_1);*/
        }
        setStatusTextBar();
    }

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusTextBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(getColor())) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    protected int getColor() {
        return getResources().getColor(R.color.black);
    }

    /**
     * 初始化布局中的空间，首先要调用setContentView
     */
    protected void findViews(Bundle savedInstanceState) {

    }

    /**
     * 初始化本地数据
     */
    protected void initViews() {

    }

    /**
     * 添加监听器
     */
    protected void addListeners() {

    }


    /**
     * 在onCreate中请求服务
     */
    protected void requestOnCreate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getInstance().removeActivity(this);
    }

    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(mBaseActivity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mBaseActivity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
}
