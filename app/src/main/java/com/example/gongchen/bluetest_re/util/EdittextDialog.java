package com.example.gongchen.bluetest_re.util;

import android.content.Context;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gongchen.bluetest_re.R;

public class EdittextDialog extends BaseDialog {

    private TextView tvTitle;
    private EditText tvContent;
    private TextView tvCancel;
    private TextView tvSure;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public EdittextDialog(Context context) {
        super(context, R.style.dim_dialog);
        mContext = context;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        // 需要调用系统的super.show()来调用onCreate来实例化view

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.edittext_dialog;
    }

    public void setBackgroundWhite() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //清除灰色背景
    }

    /**
     * 设置灰色度
     * @param alip
     */
    public void setBlackAlip(float alip) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = alip;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    @Override
    protected void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (EditText) findViewById(R.id.tvEdittext);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvSure = (TextView) findViewById(R.id.tvSure);
    }

    public void setTitle(String title) {
        if (tvTitle != null) tvTitle.setText(title);
    }

    public void setCancelLabel(String cancelLabel) {
        if (tvCancel != null) tvCancel.setText(cancelLabel);
    }

    public void setSurelLabel(String sureLabel) {
        if (tvSure != null) tvSure.setText(sureLabel);
    }

    public Editable getEditContent() {
        return tvContent.getText();
    }

    public void setEdittextHint(String hint) {
        tvContent.setHint(hint);
    }

    public void setLeftOnClick(View.OnClickListener leftOnClick) {
        if (tvCancel != null)
            tvCancel.setOnClickListener(leftOnClick);
    }

    public void setRightOnClick(View.OnClickListener click) {
        tvSure.setOnClickListener(click);
    }

    public void setEdittextType(int type) {
        tvContent.setInputType(type);
    }

    @Override
    protected void setWindowParam() {
        setWindowParams(-1, -2, Gravity.CENTER, 1);
    }

}
