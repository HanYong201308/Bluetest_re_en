package com.example.gongchen.bluetest_re;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gongchen.bluetest_re.base.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

//import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private int CAMERA_OK;

    private ZXingView mZXingView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mZXingView = findViewById(R.id.qr_view);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {

        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(ScanActivity.this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(ScanActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, CAMERA_OK);

            }else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
            }
        }else {
//这个说明系统版本在6.0之下，不需要动态获取权限。

        }
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        save(result);
        setTitle(getResources().getString(R.string.text_scan_results) + result);
        vibrate();
        mZXingView.startSpot(); // 延迟0.5秒后开始识别
        Toast.makeText(this, "添加设备成功", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别

    }

    public void save(String data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data+",");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}