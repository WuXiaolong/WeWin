package com.xiaomolongstudio.wewin.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.utils.AppUtils;

/**
 * 关于我们
 *
 * @author 小尛龙
 */
public class AboutUsActivity extends BaseActivity {
    private TextView versionName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        initToolbar(getString(R.string.aboutus));
        versionName = (TextView) findViewById(R.id.versionName);
        versionName.setText(getString(R.string.version) + AppUtils.getVersionName(this));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        // Handle action buttons
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
