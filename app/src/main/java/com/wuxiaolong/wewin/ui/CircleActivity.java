package com.wuxiaolong.wewin.ui;

import android.os.Bundle;

import com.xiaomolongstudio.wewin.R;

public class CircleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        initToolbar("剩友圈");
    }
}
