package com.wuxiaolong.wewin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.wuxiaolong.wewin.adapter.RecyclerViewAdapter;
import com.wuxiaolong.wewin.mvp.MainModel;
import com.wuxiaolong.wewin.mvp.MainPresenter;
import com.wuxiaolong.wewin.mvp.MainView;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.xiaomolongstudio.wewin.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.xiaomolongstudio.wewin.R.id.pullLoadMoreRecyclerView;

/**
 * 美图美句
 *
 * @author 小尛龙
 */
public class MainFragment extends BaseFragment {
    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    private int mPage = 1;
    private int position = 0;
    private String url = "http://www.juzimi.com/meitumeiju?page=";
    private boolean hasTitle = true;
    @BindView(pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    MainPresenter mMainPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(AppConstants.POSITION);
            url = getArguments().getString("url");

            if (position == 0) {
                hasTitle = true;
            } else {
                hasTitle = false;
            }
//            Random random = new Random();
//            mPage = random.nextInt(29);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPullLoadMoreRecyclerView.setStaggeredGridLayout(2);//参数为列数
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), hasTitle);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mMainPresenter.loadData(getUrl(), hasTitle);
            }

            @Override
            public void onLoadMore() {
                mPage = mPage + 1;
                mMainPresenter.loadData(getUrl(), hasTitle);

            }
        });
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(new MainView() {
            @Override
            public void showData(List<MainModel> mainList) {
                if (mPage == 1) {
                    mRecyclerViewAdapter.getmMainList().clear();
                }
                mRecyclerViewAdapter.getmMainList().addAll(mainList);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }


            @Override
            public void hideProgress() {
                Log.d("wxl", "hideProgress");
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

            }
        });
//        mMainPresenter.loadData(getUrl(), hasTitle);
        loadData();
    }

    private String getUrl() {
        return url + String.valueOf(mPage);
    }

    private void loadData() {
        Call<ResponseBody> call;
        if (position == 0) {
            call = apiStores.loadMainData(mPage);
        } else if (position == 1) {
            call = apiStores.loadMainData("shouxiemeiju", mPage);
        } else {
            call = apiStores.loadMainData("jingdianduibai", mPage);
        }
        call.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    String doc = new String(responseBody.bytes(), "UTF-8");
                    Document mDocument = Jsoup.parse(doc);
                    List<String> titleData = null;
                    if (hasTitle) {
                        titleData = new ArrayList<>();
                        Elements es = mDocument.getElementsByClass("xlistju");
                        for (Element e : es) {
                            titleData.add(e.text());
                        }
                    }
                    List<String> hrefData = new ArrayList<>();
                    Elements es1 = mDocument.getElementsByClass("chromeimg");
                    for (Element e : es1) {
                        hrefData.add(e.attr("src"));
                    }
                    List<MainModel> mainList = new ArrayList<>();
                    for (int i = 0; i < hrefData.size(); i++) {
                        MainModel mainModel = new MainModel();
                        if (hasTitle) {
                            mainModel.setTitle(titleData.get(i));
                        }
                        mainModel.setIamgeUrl(hrefData.get(i));
                        mainList.add(mainModel);
                    }
                    if (mPage == 1) {
                        mRecyclerViewAdapter.getmMainList().clear();
                    }
                    mRecyclerViewAdapter.getmMainList().addAll(mainList);
                    mRecyclerViewAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
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
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        });
        addCalls(call);
    }

    @Override
    public void onDestroy() {
        mMainPresenter.detachView();
        super.onDestroy();
    }

}
