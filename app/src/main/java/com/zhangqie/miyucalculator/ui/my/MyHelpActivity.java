package com.zhangqie.miyucalculator.ui.my;

import android.widget.TextView;

import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/14.
 */

public class MyHelpActivity extends BaseActivity{
    @Bind(R.id.home_top_name)
    TextView homeTopName;

    @Override
    protected int setMainLayout() {
        return R.layout.my_help_activity;
    }

    @Override
    protected void initEvents() {
        homeTopName.setText(R.string.caculator_help);
    }


    @Override
    protected void initBeforeData() {

    }


    @OnClick(R.id.home_tour_close)
    public void onClick() {
        finish();
    }
}
