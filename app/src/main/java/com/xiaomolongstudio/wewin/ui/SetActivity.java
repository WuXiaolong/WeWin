package com.xiaomolongstudio.wewin.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.utils.AppUtils;

/**
 * 设置
 *
 * @author 小尛龙
 */
public class SetActivity extends BaseActivity implements OnClickListener {
    private TextView version;

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initToolbar(getString(R.string.set));
        initView();
    }

    private void initView() {
        findViewById(R.id.recommend).setOnClickListener(this);
        findViewById(R.id.good).setOnClickListener(this);
        findViewById(R.id.feedback).setOnClickListener(this);
        findViewById(R.id.sign_out).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        findViewById(R.id.update_log).setOnClickListener(this);
        findViewById(R.id.works_show).setOnClickListener(this);
        findViewById(R.id.sponsored_author).setOnClickListener(this);
        findViewById(R.id.aboutus).setOnClickListener(this);
        findViewById(R.id.help).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        version = (TextView) findViewById(R.id.version);
        version.setText("v" + AppUtils.getVersionName(this));
    }

    @Override
    public void onClick(View v) {
        Builder builder = new Builder(SetActivity.this);
        switch (v.getId()) {
            case R.id.feedback:
                FeedbackAgent agent = new FeedbackAgent(SetActivity.this);
                agent.startFeedbackActivity();

                break;
            case R.id.good:
                AppUtils.marketDownload(SetActivity.this,
                        "com.xiaomolongstudio.wewin");

                break;
            case R.id.recommend:
                String shareContent = SetActivity.this.getResources().getString(
                        R.string.share_content);
                Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
                intent.setType("text/plain"); // 分享发送的数据类型
                intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
                startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
                break;
            case R.id.aboutus:

                startActivity(new Intent(SetActivity.this, AboutUsActivity.class));

                break;
            case R.id.works_show:

                startActivity(new Intent(SetActivity.this, WorksShowActivity.class));

                break;
            case R.id.help:

                startActivity(new Intent(SetActivity.this, HelpActivity.class));

                break;
            case R.id.sponsored_author:

                builder.setMessage(Html
                        .fromHtml("好的产品需要更多的赞助，来奉献您的一份力量。开发者支付宝账号：1413129987@qq.com，作者在此谢过。"));
                builder.setTitle("邀请赞助");
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                builder.create().show();

                break;
            case R.id.update:

                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,
                                                 UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(SetActivity.this,
                                        updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(SetActivity.this, "当前已是最新版本",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(SetActivity.this,
                                        "温馨提示，当前无wifi连接， 只在wifi下更新", Toast.LENGTH_LONG)
                                        .show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(SetActivity.this, "网络不给力",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                UmengUpdateAgent.update(this);
                break;
            case R.id.update_log:

                builder.setMessage(Html.fromHtml(getString(R.string.update_log)));
                builder.setTitle("更新日志");
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                builder.create().show();

                break;
            case R.id.clear:
                builder.setMessage("请定时清除缓存？");
                builder.setTitle("温馨提示");
                builder.setPositiveButton("清除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SetActivity.this, "清除成功",
                                        Toast.LENGTH_LONG).show();
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

}
