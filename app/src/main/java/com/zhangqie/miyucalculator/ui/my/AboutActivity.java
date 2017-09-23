package com.zhangqie.miyucalculator.ui.my;

import android.view.View;
import android.widget.TextView;

import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;
import com.zhangqie.miyucalculator.util.UtilDao;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/13.
 */

public class AboutActivity extends BaseActivity{

    @Bind(R.id.home_top_name)
    TextView homeTopName;
    @Bind(R.id.app_version)
    TextView appVersion;

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
        try {
            appVersion.setText(UtilDao.getVersion(this).replace("."," . "));
        } catch (Exception e) {
            appVersion.setText("V2 . 2 . 1");
        }
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
