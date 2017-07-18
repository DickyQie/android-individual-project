package com.zhangqie.miyucalculator.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.wx.Constants;

import java.util.ArrayList;

/**
 * Created by zhangqie on 2017/7/17.
 */

public class UtilDao {


    /****
     * 获取版本号
     * @param activity
     * @return
     */
    public static final String getVersion(Activity activity) {
            try {
                    PackageManager manager = activity.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
                    String version ="V "+info.versionCode+"."+info.versionName;
                    return  version;
                } catch (Exception e) {
                     return "V 2.0.1";
            }
         }


    /***
     *
     * @param mTencent
     * @param activity
     */
    public static final void SHOWSHAREQQ(Tencent mTencent, Activity activity) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "歆语计算器");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "歆语混合计算器，触手可及，畅享运算");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://shouji.baidu.com/software/21842018.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://img.r1.market.hiapk.com/data/upload/2017/07_15/15/72_72_20170715031045_7155.png");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        mTencent.shareToQQ(activity, params, null);
    }


    /***
     *
     * @param mTencent
     * @param activity
     */
    public static final void SHOWSHAREQQZONE(Tencent mTencent, Activity activity) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "歆语计算器");
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "歆语混合计算器，触手可及，畅享运算");
        params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                "http://shouji.baidu.com/software/21842018.html");
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add("http://img.r1.market.hiapk.com/data/upload/2017/07_15/15/72_72_20170715031045_7155.png");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQzone(activity, params, null);
    }

    public static final void SHOWPOPUPWINDOW(final Tencent tencent, View view, final PopupWindow popupWindow, final LinearLayout linearLayout, final Activity activity){
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_qq);
        TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_qqzone);
        TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_weixin);
        TextView bt4 = (TextView) view.findViewById(R.id.item_popupwindows_weixinzone);
        TextView bt5 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                linearLayout.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SHOWSHAREQQ(tencent,activity);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SHOWSHAREQQZONE(tencent,activity);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onShowWx(activity,1);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onShowWx(activity,2);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                linearLayout.clearAnimation();
            }
        });
    }

    /****
     * 微信分享
     * @param activity
     * @param i 好友或者朋友圈
     */
    private static final void onShowWx(Activity activity,int i) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://shouji.baidu.com/software/21842018.html";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "歆语计算器";
        msg.description = "语混合计算器，触手可及，畅享运算";
        Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = i == 1 ? SendMessageToWX.Req.WXSceneSession
                : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
    private static final String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
}
