package com.example.gongchen.bluetest_re.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gongchen.bluetest_re.R;

public class TitleBar extends RelativeLayout {
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private Context mContext;
    private TextView tvBarright;
    private ImageView imageBarright;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_titlebar_layout, this, true);
        rlBack = ((RelativeLayout) inflate.findViewById(R.id.rlBarBack));
        tvTitle = ((TextView) inflate.findViewById(R.id.tvBarTitle));
        tvBarright = (TextView) inflate.findViewById(R.id.tvBarright);
        imageBarright = inflate.findViewById(R.id.imageBarright);
        rlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }

    public void setRightImage(int i) {
        if (i == 0) {
            imageBarright.setVisibility(VISIBLE);
        } else {
            imageBarright.setVisibility(GONE);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(GONE);
        }
    }

    public void setRightGone(boolean isGone) {
        if (isGone) {
            tvBarright.setVisibility(GONE);
        } else {
            tvBarright.setVisibility(VISIBLE);
        }
    }

    public void setRightTextColor(int color) {
        tvBarright.setTextColor(color);
    }

    public void setRightTextSize(int size) {
        tvBarright.setTextSize(size);
    }

    public String getTitle() {
        if (!TextUtils.isEmpty(tvTitle.getText())) {
            return tvTitle.getText().toString();
        } else {
            return "";
        }
    }

    public void onRightImageOnclickListener(OnClickListener onClickListener) {
        imageBarright.setOnClickListener(onClickListener);
    }

    public void setBackOnclickListener(OnClickListener leftOnclickListener) {
        rlBack.setOnClickListener(leftOnclickListener);
    }

    public void setRigntbg(int res) {
        tvBarright.setBackgroundResource(res);
    }

    public void setRightText(String message) {
        tvBarright.setText(message);
    }
    
    public void setRightOnclickListener(OnClickListener rightOnClickListener) {
        tvBarright.setOnClickListener(rightOnClickListener);
    }
}