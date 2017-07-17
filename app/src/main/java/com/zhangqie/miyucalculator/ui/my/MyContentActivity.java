package com.zhangqie.miyucalculator.ui.my;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;
import com.zhangqie.miyucalculator.widget.RoundedCornersTransformation;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/13.
 */

public class MyContentActivity extends BaseActivity {


    @Bind(R.id.home_top_name)
    TextView homeTopName;
    @Bind(R.id.my_logo)
    ImageView myLogo;

    @Override
    protected int setMainLayout() {
        return R.layout.my_content_activity;
    }

    @Override
    protected void initEvents() {
        homeTopName.setText(R.string.mi_content_msg);
        int resourceId = R.mipmap.qwewqe;
        Glide.with(this)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new RoundedCornersTransformation(this,25))
                .into(myLogo);
    }


    @Override
    protected void initBeforeData() {

    }


    @OnClick(R.id.home_tour_close)
    public void onClick() {
        finish();
    }
}
