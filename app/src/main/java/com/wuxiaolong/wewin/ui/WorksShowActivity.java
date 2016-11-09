package com.wuxiaolong.wewin.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xiaomolongstudio.wewin.R;
import com.wuxiaolong.wewin.utils.AppUtils;

/**
 * 作品秀
 */
public class WorksShowActivity extends BaseActivity implements OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.works_show);
        initToolbar("作品秀");
        findViewById(R.id.weiyan).setOnClickListener(this);
        findViewById(R.id.danhuaer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weiyan:
                AppUtils.marketDownload(WorksShowActivity.this,
                        "com.android.xiaomolongstudio.weiyan");
                break;
            case R.id.danhuaer:
                AppUtils.marketDownload(WorksShowActivity.this,
                        "com.android.xiaomolongstudio.danhuaer");
                break;

            default:
                break;
        }

    }


}
