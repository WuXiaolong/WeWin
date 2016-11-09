package com.wuxiaolong.wewin.ui.tngounews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;
import android.widget.TextView;

import com.wuxiaolong.androidutils.library.TimeUtil;
import com.wuxiaolong.wewin.model.TngouNewsDetailModel;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.ui.BaseActivity;
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
    @BindView(R.id.webview)
    WebView webview;
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
//                    message.setText(Html.fromHtml(model.getMessage()));
                    webview.loadData(model.getMessage(), "text/html; charset=UTF-8", null);
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

}
