package com.wuxiaolong.wewin.ui.myblog;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxiaolong.androidutils.library.LogUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.ui.BaseFragment;
import com.wuxiaolong.wewin.ui.WebViewActivity;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.xiaomolongstudio.wewin.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MyBlogFragment extends BaseFragment {

    private DataAdapter dataAdapter;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_blog, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        loadMyBlog();
    }

    private void initView() {
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.setLinearLayout();
        dataAdapter = new DataAdapter();
        pullLoadMoreRecyclerView.setAdapter(dataAdapter);
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadMyBlog();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadMyBlog();
            }
        });


    }

    private void loadMyBlog() {
        Call<ResponseBody> call;
        if (page == 1) {
            call = apiStores.loadMyBlog();
        } else {
            call = apiStores.loadMyBlog(page);
        }
        call.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    Document document = Jsoup.parse(new String(responseBody.bytes(), "UTF-8"));
                    List<Element> titleElementList = new ArrayList<>();
                    Elements titleElements = document.getElementsByClass("post-title-link");
                    for (Element element : titleElements) {
                        titleElementList.add(element);
                        //LogUtil.d("text=" + element.text());
                        //LogUtil.d("href=" + element.attr("href"));
                    }
                    List<Element> timeElementList = new ArrayList<>();
                    Elements timeElements = document.getElementsByClass("post-time");
                    for (Element element : timeElements) {
                        //LogUtil.d("time=" + element.getElementsByTag("time").text());
                        timeElementList.add(element);
                    }
                    //Elements categoryElements = document.getElementsByClass("post-category");
                    //for (Element element : categoryElements) {
                    //    LogUtil.d("category=" + element.getElementsByTag("a").text());
                    //}
                    List<Element> bodyElementList = new ArrayList<>();
                    Elements bodyElements = document.getElementsByClass("post-body");
                    for (Element element : bodyElements) {
                        LogUtil.d("body=" + element.html());
                        bodyElementList.add(element);
                    }
                    if (page == 1) {
                        dataAdapter.clear();
                    }
                    dataAdapter.addAll(titleElementList, timeElementList, bodyElementList);
                    if (titleElementList.size() < 8) {
                        //因为我的博客一页8条数据，当小于8时，说明没有下一页了
                        pullLoadMoreRecyclerView.setHasMore(false);
                    } else {
                        pullLoadMoreRecyclerView.setHasMore(true);
                    }

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
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        });
        addCalls(call);
    }


    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        private List<Element> titleElementList = new ArrayList<>();
        private List<Element> timeElementList = new ArrayList<>();
        private List<Element> bodyElementList = new ArrayList<>();


        public void addAll(List<Element> titleElementList, List<Element> timeElementList, List<Element> bodyElementList) {
            this.titleElementList.addAll(titleElementList);
            this.timeElementList.addAll(timeElementList);
            this.bodyElementList.addAll(bodyElementList);
            notifyDataSetChanged();
        }

        public void clear() {
            this.titleElementList.clear();
            this.timeElementList.clear();
            this.bodyElementList.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_blog_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Element titleElement = titleElementList.get(position);
            holder.title.setText(titleElement.text());
            Element timeElement = timeElementList.get(position);
            holder.time.setText(timeElement.getElementsByTag("time").text());
            Element bodyElement = bodyElementList.get(position);
            holder.body.setText(Html.fromHtml(bodyElement.html()));
        }

        @Override
        public int getItemCount() {
            return titleElementList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.time)
            TextView time;
            @BindView(R.id.body)
            TextView body;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Element titleElement = titleElementList.get(getLayoutPosition());
                        startActivity(new Intent(mActivity, WebViewActivity.class)
                                .putExtra(AppConstants.WEBVIEW_TITLE, titleElement.text())
                                .putExtra(AppConstants.WEBVIEW_URL, AppConstants.MY_Blog_URL + titleElement.attr("href")));
                        LogUtil.d("uuu=" + AppConstants.MY_Blog_URL + titleElement.attr("href"));
                    }
                });
            }
        }
    }
}
