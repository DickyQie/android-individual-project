package com.zhangqie.miyucalculator.ui.my;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangqie.miyucalculator.R;
import com.zhangqie.miyucalculator.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangqie on 2017/7/13.
 */

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.home_top_name)
    TextView homeTopName;
    @Bind(R.id.feedback_edittext)
    EditText feedbackEdittext;
    @Bind(R.id.tel_email)
    EditText telEmail;
    @Override
    protected int setMainLayout() {
        return R.layout.my_feedback;
    }

    @Override
    protected void initEvents() {
        homeTopName.setText(R.string.calculator_feedback);
    }

    @Override
    protected void initBeforeData() {

    }

    @OnClick({R.id.home_tour_close, R.id.feedback_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tour_close:
                finish();
                break;
            case R.id.feedback_submit:
                if (feedbackEdittext.getText().toString().length()<2)
                {
                    showToastShort(R.string.content_null);
                    return;
                }
                String telRegex = "[1][358]\\d{9}";
                if (!telEmail.getText().toString().trim().matches(telRegex)) {
                    showToastShort(R.string.is_tel);
                    return;
                }
                showFeedBackContent();
                break;
        }
    }


    private void showFeedBackContent() {
        showToastShort(R.string.is_feedback_true);
        feedbackEdittext.setText("");
        telEmail.setText("");
    }
}
