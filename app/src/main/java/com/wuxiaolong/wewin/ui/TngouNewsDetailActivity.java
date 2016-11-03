package com.wuxiaolong.wewin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wuxiaolong.androidutils.library.TimeUtil;
import com.wuxiaolong.wewin.model.TngouNewsDetailModel;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TngouNewsDetailActivity extends BaseActivity {

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int id;
    TngouNewsDetailModel tngouNewsDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tngou_news_detail);
        ButterKnife.bind(this);
        id = this.getIntent().getIntExtra(AppConstants.ID, 0);
        setRefreshing(true);
        loadData();
    }

    private void setRefreshing(final boolean refreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }

    private void loadData() {
        Call<TngouNewsDetailModel> call = apiStores.loadTngouNewsDetail(id);
        call.enqueue(new RetrofitCallback<TngouNewsDetailModel>() {
            @Override
            public void onSuccess(TngouNewsDetailModel model) {
                if (model.isStatus()) {
                    tngouNewsDetailModel = model;
                    initToolbar(model.getTitle());
                    title.setText(model.getTitle());
                    time.setText(TimeUtil.unixTimestamp2BeijingTime(model.getTime(), "yyyy-MM-dd HH:mm:ss"));
                    message.setText(Html.fromHtml(model.getMessage()));
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                toastShow(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                toastShow(t.getMessage());
            }

            @Override
            public void onFinish() {
                setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
            }
        });
        addCalls(call);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tngou_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_fromurl) {
            if (tngouNewsDetailModel != null)
                startActivity(new Intent(mActivity, WebViewActivity.class)
                        .putExtra(AppConstants.WEBVIEW_TITLE, tngouNewsDetailModel.getTitle())
                        .putExtra(AppConstants.WEBVIEW_URL, tngouNewsDetailModel.getFromurl()));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
