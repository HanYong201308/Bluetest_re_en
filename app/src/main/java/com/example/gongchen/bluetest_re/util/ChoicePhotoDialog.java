package com.example.gongchen.bluetest_re.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.gongchen.bluetest_re.R;

import java.util.Objects;

public class ChoicePhotoDialog extends Dialog implements View.OnClickListener  {
    private TextView start_power;
    private TextView no_PowerIm;
    private TextView tvCancel;
    String content;

    private int selectPhotoType = 0;//初始化状态

    private int state;

    private View line1;

    public ChoicePhotoDialog(Context context) {
        this(context, R.style.GraphVerifyDialogTheme);
    }

    private ChoicePhotoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public void setTvCameraVisibility(int state) {
        this.state = state;
        // Log.e("setTvCameraVisibility", "setTvCameraVisibility: " );

    }


    public void setTextVisibility(boolean tvCameraGone, boolean tvTakePhotoGone) {
        if (tvCameraGone) {
            start_power.setVisibility(View.VISIBLE);
        } else {
            start_power.setVisibility(View.GONE);
        }

        if (tvTakePhotoGone) {
            no_PowerIm.setVisibility(View.VISIBLE);
        } else {
            no_PowerIm.setVisibility(View.GONE);
        }
    }

    public void setTextContent(String tvCameraContent, String tvTakePhotoContent) {
        if (tvCameraContent != null) {
            start_power.setText(tvCameraContent);
        }

        if (tvTakePhotoContent != null) {
            no_PowerIm.setText(tvCameraContent);
        }
    }

    public void setTextColor(int tvCameraColor, int tvTakePhotoColor) {
        if (tvCameraColor != 0) {
            start_power.setTextColor(tvCameraColor);
        }
        if (tvTakePhotoColor != 0) {
            no_PowerIm.setText(tvTakePhotoColor);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_choice_photo);
        //  Log.e("setTvCameraVisibility", "onCreate: " );
        start_power = findViewById(R.id.start_power);
        line1 = findViewById(R.id.line1);
        no_PowerIm = findViewById(R.id.no_PowerIm);
        tvCancel = findViewById(R.id.tv_cancel);
        start_power.setOnClickListener(this);
        no_PowerIm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        if (line1 != null) line1.setVisibility(state);
        if (start_power != null) start_power.setVisibility(state);
    }

    public String getContent() {
        return content;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_power:
                content = getContext().getResources().getString(R.string.start_PowerIm);
                dismiss();
                break;
            case R.id.no_PowerIm:
                content = getContext().getResources().getString(R.string.no_PowerIm);
                dismiss();
                break;
            case R.id.tv_cancel:

                dismiss();
                break;
        }
    }
}
