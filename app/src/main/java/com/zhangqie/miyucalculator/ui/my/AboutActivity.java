package com.zhangqie.miyucalculator.ui.my;

import android.view.View;
import android.widget.TextView;

import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/13.
 */

public class AboutActivity extends BaseActivity{

    @Bind(R.id.home_top_name)
    TextView homeTopName;

    @Override
    protected int setMainLayout() {
        return R.layout.about_activity;
    }
    @Override
    protected void initEvents() {
        homeTopName.setText(R.string.calculator_about);
    }
    @Override
    protected void initBeforeData() {
    }

    @OnClick({R.id.home_tour_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tour_close:
                finish();
                break;
        }
    }

}
