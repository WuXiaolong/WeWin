package com.xiaomolongstudio.wewin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.adapter.RecyclerViewAdapter;
import com.xiaomolongstudio.wewin.mvp.MainModel;
import com.xiaomolongstudio.wewin.mvp.MainPresenter;
import com.xiaomolongstudio.wewin.mvp.MainView;

import java.util.List;

import butterknife.Bind;

/**
 * 美图美句
 *
 * @author 小尛龙
 */
public class MainFragment extends BaseFragment {
    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    private int mPage = 1;
    private String url = "http://www.juzimi.com/meitumeiju?page=";
    private boolean hasTitle = true;
    @Bind(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    MainPresenter mMainPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");

            if (url.equals("http://www.juzimi.com/meitumeiju?page=")) {
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
                if (mRecyclerViewAdapter == null) {
                    mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mainList, hasTitle);
                    mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
                } else {
                    if (mPage == 1) {
                        mRecyclerViewAdapter.getmMainList().clear();
                    }
                    mRecyclerViewAdapter.getmMainList().addAll(mainList);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void hideProgress() {
                Log.d("wxl", "hideProgress");
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

            }
        });
        mMainPresenter.loadData(getUrl(), hasTitle);
    }

    private String getUrl() {
        return url + String.valueOf(mPage);
    }


    @Override
    public void onDestroy() {
        mMainPresenter.detachView();
        super.onDestroy();
    }

}
