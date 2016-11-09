package com.wuxiaolong.wewin.ui.set;

import android.os.Bundle;

import com.wuxiaolong.wewin.ui.BaseActivity;
import com.xiaomolongstudio.wewin.R;

public class CircleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        initToolbar("剩友圈");
    }
}
