package com.wuxiaolong.wewin.ui.myblog;

import android.os.Bundle;

import com.wuxiaolong.wewin.ui.BaseActivity;
import com.xiaomolongstudio.wewin.R;

public class MyBlogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog);
        initToolbar("吴小龙同學");
    }

}
