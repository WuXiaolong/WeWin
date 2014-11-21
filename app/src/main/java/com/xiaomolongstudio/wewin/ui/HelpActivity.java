package com.xiaomolongstudio.wewin.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xiaomolongstudio.wewin.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 帮助说明
 * 
 * @author 小尛龙
 * 
 */
public class HelpActivity extends BaseActivity {
    @InjectView(R.id.introduction)
    TextView introduction;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
        ButterKnife.inject(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("剩友圈");
        introduction.setText(Html.fromHtml(getResources().getString(
                R.string.introduction)));

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

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
