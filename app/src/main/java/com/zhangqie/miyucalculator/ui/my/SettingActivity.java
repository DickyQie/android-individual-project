package com.zhangqie.miyucalculator.ui.my;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.tauth.Tencent;
import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;
import com.zhangqie.miyucalculator.util.UtilDao;
import com.zhangqie.miyucalculator.widget.PreferenceItem;
import com.zhangqie.miyucalculator.widget.RoundedCornersTransformation;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/13.
 */

public class SettingActivity extends BaseActivity {

    @Bind(R.id.home_top_name)
    TextView homeTopName;
    @Bind(R.id.my_share)
    PreferenceItem myShare;
    @Bind(R.id.my_clear_data)
    PreferenceItem myClearData;
    @Bind(R.id.my_update_version)
    PreferenceItem myUpdateVersion;
    @Bind(R.id.my_help)
    PreferenceItem myHelp;
    @Bind(R.id.my_logo)
    ImageView myLogo;


    PopupWindow popupWindow;
    LinearLayout linearLayout;
    Tencent mTencent;

    @Override
    protected int setMainLayout() {
        return R.layout.my_setting_activity;
    }

    @Override
    protected void initEvents() {
        homeTopName.setText(R.string.calculator_setting);
        int resourceId = R.mipmap.qwewqe;
        Glide.with(this)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new RoundedCornersTransformation(this,25))
                .into(myLogo);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(getString(R.string.appId), SettingActivity.this);
        }
    }

    @Override
    protected void initBeforeData() {
        myShare.setTitle(R.string.my_share);
        myClearData.setTitle(R.string.my_clear_data);
        myUpdateVersion.setTitle(R.string.my_updates_version);
        myHelp.setTitle(R.string.my_help);
    }

    @OnClick({R.id.home_tour_close,R.id.my_share,R.id.my_clear_data,R.id.my_update_version,R.id.my_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tour_close:
                finish();
                break;
            case R.id.my_share:
                showShareThree();
                linearLayout.startAnimation(AnimationUtils.loadAnimation(
                        SettingActivity.this, R.anim.activity_translate_in));
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.my_clear_data:
                showNormalDialog();
                break;
            case R.id.my_update_version:
                showNormalDialog1();
                break;
            case R.id.my_help:
                openActivity(MyHelpActivity.class);
                break;

        }
    }








    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SettingActivity.this);
        normalDialog.setTitle(R.string.dialog_title);
        normalDialog.setMessage(R.string.dialog_message);
        normalDialog.setPositiveButton(R.string.dialog_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToastShort(R.string.data_clear);
                    }
                });
        normalDialog.setNegativeButton(R.string.dialog_close,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void showNormalDialog1(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SettingActivity.this);
        normalDialog.setTitle(R.string.dialog_title);
        normalDialog.setMessage(R.string.dialog_messge_version);
        normalDialog.setPositiveButton(R.string.dialog_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void showShareThree() {
        try {
            popupWindow = new PopupWindow(SettingActivity.this);
            View view = getLayoutInflater().inflate(R.layout.share_popupwindows, null);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_popup);
            UtilDao.SHOWPOPUPWINDOW(mTencent,view,popupWindow,linearLayout,SettingActivity.this);
        } catch (Exception e) {
           showToastShort("分享失败");
        }
    }



}
