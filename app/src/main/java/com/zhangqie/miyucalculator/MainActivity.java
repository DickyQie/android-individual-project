package com.zhangqie.miyucalculator;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tencent.tauth.Tencent;
import com.zhangqie.miyucalculator.base.BaseActivity;
import com.zhangqie.miyucalculator.ui.my.AboutActivity;
import com.zhangqie.miyucalculator.ui.my.FeedBackActivity;
import com.zhangqie.miyucalculator.ui.my.MyContentActivity;
import com.zhangqie.miyucalculator.ui.my.SettingActivity;
import com.zhangqie.miyucalculator.util.Calculator;
import com.zhangqie.miyucalculator.util.UtilDB;
import com.zhangqie.miyucalculator.util.UtilDao;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.text_num1)
    TextView textNum1;
    @Bind(R.id.text_num2)
    TextView textNum2;
    @Bind(R.id.item7)
    TextView item7;
    @Bind(R.id.item8)
    TextView item8;
    @Bind(R.id.item9)
    TextView item9;
    @Bind(R.id.item4)
    TextView item4;
    @Bind(R.id.item5)
    TextView item5;
    @Bind(R.id.item6)
    TextView item6;
    @Bind(R.id.item1)
    TextView item1;
    @Bind(R.id.item2)
    TextView item2;
    @Bind(R.id.item3)
    TextView item3;
    @Bind(R.id.item0)
    TextView item0;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    Tencent mTencent;
    @Override
    protected int setMainLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initBeforeData() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(getString(R.string.appId), MainActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openActivity(AboutActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            openActivity(MyContentActivity.class);
        }else if (id==R.id.nav_manage){
            openActivity(SettingActivity.class);
        }
        else if (id == R.id.nav_slideshow) {
            openActivity(FeedBackActivity.class);
        }
        else if (id == R.id.nav_share) {
            UtilDao.SHOWSHAREQQ(mTencent,MainActivity.this);
        }
        else if (id == R.id.nav_send) {
            openActivity(AboutActivity.class);
        }
        return true;
    }


    @OnClick({R.id.item_clear, R.id.item_delete, R.id.item_cfs, R.id.item_cufs, R.id.item7, R.id.item8, R.id.item9, R.id.item_add, R.id.item4, R.id.item5, R.id.item6, R.id.item_jfs, R.id.item1, R.id.item2, R.id.item3, R.id.item_qfs, R.id.item0, R.id.item_dfs, R.id.item_update})
    public void onClick(View view) {
        String num2=textNum2.getText().toString();
        switch (view.getId()) {
            case R.id.item_clear:
                textNum1.setText("0");
                textNum2.setText("");
                break;
            case R.id.item_delete:
                if (num2.length()>1){
                    textNum2.setText(UtilDB.SHOWDELETE(num2));
                }else {
                    textNum2.setText("0");
                }
                break;
            case R.id.item_cfs:
                if (num2.length()>16){             return;         }
                if (!UtilDB.ISNUMBERQYS(num2)) {
                    if (num2.length() > 0) {
                        if (!UtilDB.ISNUMBER(UtilDB.TEXTONE(num2))) {
                            textNum2.append("×");
                        }
                    }
                }
                break;
            case R.id.item_cufs:
                if (num2.length()>16){             return;         }
                if (!UtilDB.ISNUMBERQYS(num2)) {
                    if (num2.length() > 0) {
                        if (!UtilDB.ISNUMBER(UtilDB.TEXTONE(num2))) {
                            textNum2.append("÷");
                        }
                    }
                }
                break;
            case R.id.item7:
                showTextOnliTie();
                if (num2.length()>16){             return;         }
                textNum2.append("7");
                break;
            case R.id.item8:
                showTextOnliTie();
               if (num2.length()>16){             return;         }
                textNum2.append("8");
                break;
            case R.id.item9:
                showTextOnliTie();
                if (num2.length()>16){             return;         }
                textNum2.append("9");
                break;
            case R.id.item_add:
                if (!UtilDB.ISNUMBERQYS(num2)) {
                    if (num2.length() > 0) {
                        if (!UtilDB.ISNUMBER(UtilDB.TEXTONE(num2))) {
                            textNum2.append("+");
                        }
                    }
                }
                break;
            case R.id.item4:
                showTextOnliTie();
               if (num2.length()>16){             return;         }
                textNum2.append("4");
                break;
            case R.id.item5:
                showTextOnliTie();
               if (num2.length()>16){             return;         }
                textNum2.append("5");
                break;
            case R.id.item6:
                showTextOnliTie();
                if (num2.length()>16){             return;         }
                textNum2.append("6");
                break;
            case R.id.item_jfs:
                if (num2.length()>16){             return;         }
                if (!UtilDB.ISNUMBERQYS(num2)) {

                    if (num2.length() == 1) {
                        if (!UtilDB.ISNUMBER(UtilDB.TEXTONE(num2))) {
                            textNum2.append("-");
                        }
                    } else if (num2.length() > 2) {
                        if (!(UtilDB.ISNUMBER(UtilDB.TEXTTOWONE(num2)) && UtilDB.ISNUMBER(UtilDB.TEXTONE(num2)))) {
                            textNum2.append("-");
                        }
                    } else {
                        textNum2.append("-");
                    }
                }
                break;
            case R.id.item1:
                showTextOnliTie();
                if (num2.length()>16){
                    return;
                }
                textNum2.append("1");
                break;
            case R.id.item2:
                showTextOnliTie();
                if (num2.length()>16){
                    return;
                }
                textNum2.append("2");
                break;
            case R.id.item3:
                showTextOnliTie();
                if (num2.length()>16){
                    return;
                }
                textNum2.append("3");
                break;
            case R.id.item_qfs:
                if (num2.length()>16){
                    return;
                }
                if (num2.length()>0){
                    if (!UtilDB.ISFANGSHI(num2) && UtilDB.SHOWDLENGS(num2)){
                        textNum2.append("%");
                    }else {
                        showToastShort(R.string.error_calculator_quyus);
                    }
                }
                break;
            case R.id.item0:
                if (num2.length()>16){
                    return;
                }
                if (num2.length()>1){
                    if (UtilDB.ISSHOWO(num2)){
                        if (UtilDB.ISNUMBERQYS(num2)){
                            showToastShort(R.string.error_calculator_quyuoli);
                        }else {
                            textNum2.append("0");
                        }
                    }else {
                        showToastShort(R.string.error_calculator_chushuoli);
                    }
                } else if (num2.length()>0){
                    if (!num2.equals("0")) {
                        textNum2.append("0");
                    }
                }
                break;
            case R.id.item_dfs:
                if (num2.length()>16){
                    return;
                }
                if (!UtilDB.ISNUMBERQYS(num2)){
                    if(num2.length()>0){
                        if (UtilDB.ISSHOWDES(num2)) {
                            if (UtilDB.ISFANGSHI(num2) && UtilDB.ISDIANFES(num2)) {
                                textNum2.append(".");
                            } else if (!UtilDB.ISFANGSHI(num2) && num2.indexOf(".")==-1) {
                                textNum2.append(".");
                            }
                        }
                    }
                }
                break;
            case R.id.item_update:
                if (num2.length()>1) {
                    textNum1.setText(UtilDB.setNum(num2) + "=");
                }else {
                    textNum1.setText(num2+"=");
                }
                if (UtilDB.ISNUMBERQYS(num2)) {
                    String[] strings=num2.split("%");
                    if (strings.length>1){
                        textNum2.setText(UtilDB.SHOWQYS(Integer.valueOf(strings[0]),Integer.valueOf(strings[1])));
                    }else {
                        showToastShort(R.string.error_calculator_test);
                        textNum2.setText("");
                    }
                }else if(num2.length()>2) {
                    if (UtilDB.ISFANGSHI(num2)){
                       if (!UtilDB.ISNUMBER(UtilDB.TEXTONE(num2))){
                           try {
                               //textNum2.setText(UtilDB.getDateSelects(String.valueOf(Util.opt(UtilDB.SHOWTEXTREPLACE(num2)))));

                               Calculator cal = new Calculator();
                               String ddddd=cal.exec(UtilDB.SHOWTEXTREPLACE(num2));
                               BigDecimal bd = new BigDecimal(ddddd);

                               textNum2.setText(UtilDB.getDateSelects(UtilDB.getDateSelectsNum(bd.toPlainString())));
                               String num3 = textNum2.getText().toString();
                               textNum2.setTextSize(UtilDB.setTextSize(num3));
                               String num4 = textNum1.getText().toString();
                               textNum1.setTextSize((UtilDB.setTextSize(num4)-1));
                           } catch (Exception e) {
                               showToastShort(R.string.error_calculator_error);
                               textNum1.setText("0");
                               textNum2.setText("");
                           }
                       }else {
                           textNum1.setText("0");
                           textNum2.setText("");
                           showToastShort(R.string.error_calculator_error);
                       }
                    }

                }else if (num2.length()<=2){
                    textNum2.setText(num2);
                }else {
                }
                break;
        }
        textNum2.setTextSize(UtilDB.setTextSize(num2));
    }


    private void showTextOnliTie() {
        if (textNum2.getText().toString().equals("0") && textNum2.getText().toString().length()==1) {
            textNum2.setText("");
        }
    }









    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }



}
