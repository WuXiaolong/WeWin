package com.wuxiaolong.wewin.ui.set;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.wuxiaolong.wewin.ui.BaseActivity;
import com.wuxiaolong.wewin.ui.MainActivity;
import com.wuxiaolong.wewin.ui.WorksShowActivity;
import com.wuxiaolong.wewin.utils.AppUtils;
import com.xiaomolongstudio.wewin.R;

/**
 * 设置
 */
public class SetActivity extends BaseActivity implements OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initToolbar(getString(R.string.set));
        initView();
    }

    private void initView() {
        findViewById(R.id.recommend).setOnClickListener(this);
        findViewById(R.id.good).setOnClickListener(this);
        findViewById(R.id.sign_out).setOnClickListener(this);
        findViewById(R.id.works_show).setOnClickListener(this);
        findViewById(R.id.sponsored_author).setOnClickListener(this);
        findViewById(R.id.aboutus).setOnClickListener(this);
        findViewById(R.id.circle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Builder builder = new Builder(SetActivity.this);
        switch (v.getId()) {
            case R.id.good:
                AppUtils.marketDownload(SetActivity.this, "com.xiaomolongstudio.wewin");

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
            case R.id.circle:

                startActivity(new Intent(SetActivity.this, CircleActivity.class));

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

            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_old) {
            startActivity(new Intent(mActivity, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
