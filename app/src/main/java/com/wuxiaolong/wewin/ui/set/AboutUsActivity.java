package com.wuxiaolong.wewin.ui.set;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.wewin.ui.BaseActivity;
import com.wuxiaolong.wewin.utils.AppUtils;
import com.xiaomolongstudio.wewin.R;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {
    private TextView versionName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        initToolbar(getString(R.string.aboutus));
        versionName = (TextView) findViewById(R.id.versionName);
        versionName.setText(getString(R.string.version) + AppUtils.getVersionName(this));
        findViewById(R.id.aboutusText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/WuXiaolong/WeWin");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }


}
