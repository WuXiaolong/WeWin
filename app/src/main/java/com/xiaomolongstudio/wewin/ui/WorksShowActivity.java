package com.xiaomolongstudio.wewin.ui;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.umeng.analytics.MobclickAgent;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.utils.AppUtils;
/**
 * 作品秀
 * @author 小尛龙
 *
 */
public class WorksShowActivity extends BaseActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.works_show);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("作品秀");
		findViewById(R.id.weiyan).setOnClickListener(this);
		findViewById(R.id.jiong).setOnClickListener(this);
		findViewById(R.id.hua).setOnClickListener(this);
		findViewById(R.id.ufosay).setOnClickListener(this);
		findViewById(R.id.danhuaer).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Builder builder = new Builder(WorksShowActivity.this);
		switch (v.getId()) {
		case R.id.weiyan:
			builder.setMessage("请确认当前是WIFI下载，以免过多地消耗流量。");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AppUtils.marketDownload(WorksShowActivity.this,
									"com.android.xiaomolongstudio.weiyan");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;
		case R.id.jiong:
			builder.setMessage("请确认当前是WIFI下载，以免过多地消耗流量。");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AppUtils.marketDownload(WorksShowActivity.this,
									"com.android.xiaomostudio.vibrator");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;
		case R.id.hua:
			builder.setMessage("请确认当前是WIFI下载，以免过多地消耗流量。");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AppUtils.marketDownload(WorksShowActivity.this,
									"com.android.xiaomolongstudio.hua");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;
		case R.id.ufosay:
			builder.setMessage("请确认当前是WIFI下载，以免过多地消耗流量。");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AppUtils.marketDownload(WorksShowActivity.this,
									"com.xiaomolong.ufosay");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;
		case R.id.danhuaer:
			builder.setMessage("请确认当前是WIFI下载，以免过多地消耗流量。");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AppUtils.marketDownload(WorksShowActivity.this,
									"com.android.xiaomolongstudio.danhuaer");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			break;

		default:
			break;
		}

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
