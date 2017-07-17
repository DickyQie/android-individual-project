package com.zhangqie.miyucalculator.ui.my;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;
import com.zhangqie.miyucalculator.widget.PreferenceItem;
import com.zhangqie.miyucalculator.widget.RoundedCornersTransformation;

import java.util.ArrayList;

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
            mTencent = Tencent.createInstance(mAppid, SettingActivity.this);
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
                InitImg();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        SettingActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.my_clear_data:
               // dialog1();
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


    PopupWindow pop;
    LinearLayout ll_popup;
    public void InitImg() {
        pop = new PopupWindow(SettingActivity.this);
        View view = getLayoutInflater().inflate(R.layout.map_popupwindows,
                null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_qq);
        TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_qqzone);
        TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_weixin);
        TextView bt4 = (TextView) view.findViewById(R.id.item_popupwindows_weixinzone);
        TextView bt5 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickShare();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareToQQzone();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastShort("开发中......");
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastShort("开发中......");
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }


    Tencent mTencent;
    String mAppid="1106218221";


    //qq分享
    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "歆语计算器");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "歆语混合计算器，触手可及，畅享运算");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://shouji.baidu.com/software/8655742.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(SettingActivity.this, params, new BaseUiListener1());
    }
    //回调接口  (成功和失败的相关操作)
    private class BaseUiListener1 implements IUiListener {

        @Override
        public void onComplete(Object response) {
            doComplete(response);
        }

        protected void doComplete(Object values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }

    @SuppressWarnings("unused")
    private void shareToQQzone() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "歆语计算器");
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "歆语混合计算器，触手可及，畅享运算");
        params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                "http://shouji.baidu.com/software/8655742.html");
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

         /*   params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                    QQShare.SHARE_TO_QQ_TYPE_DEFAULT);*/
                mTencent.shareToQzone(SettingActivity.this, params, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                    }

                    @Override
                    public void onError(UiError e) {
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }


}
