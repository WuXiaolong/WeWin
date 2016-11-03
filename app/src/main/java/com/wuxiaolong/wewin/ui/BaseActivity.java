package com.wuxiaolong.wewin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wuxiaolong.wewin.retrofit.ApiStores;
import com.wuxiaolong.wewin.retrofit.AppClient;
import com.xiaomolongstudio.wewin.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * 基本
 *
 * @author 小尛龙
 */
public class BaseActivity extends AppCompatActivity {

    private List<Call> calls;
    public ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mActivity = this;
    }

    public Toolbar initToolbarAsHome(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitleView.setText(title);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);

        }
        return toolbar;
    }

    public Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(title);
        TextView mTitleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitleView.setText(title);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolbar(int title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    @Override
    protected void onDestroy() {
        callCancel();
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }
}
