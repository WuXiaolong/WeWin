package com.wuxiaolong.wewin.ui.tngounews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.wuxiaolong.wewin.model.TngouNewsModel;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.ui.BaseFragment;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.wuxiaolong.wewin.utils.ImageLoader;
import com.xiaomolongstudio.wewin.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TngouNewsFragment extends BaseFragment {
    DataAdapter dataAdapter;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private int page = 1;
    int rows = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tngou_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        loadData();
    }

    private void initView() {
        pullLoadMoreRecyclerView.setLinearLayout();
        pullLoadMoreRecyclerView.setRefreshing(true);
        dataAdapter = new DataAdapter();
        pullLoadMoreRecyclerView.setAdapter(dataAdapter);
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
    }

    private void loadData() {
        Call<TngouNewsModel> call = apiStores.loadTngouNews(page, rows);
        call.enqueue(new RetrofitCallback<TngouNewsModel>() {
            @Override
            public void onSuccess(TngouNewsModel model) {
                if (model.isStatus()) {
                    if (page == 1) {
                        dataAdapter.clear();
                    }
                    dataAdapter.addAll(model.getTngou());
                    if (model.getTngou().size() < rows) {
                        pullLoadMoreRecyclerView.setHasMore(false);
                    } else {
                        pullLoadMoreRecyclerView.setHasMore(true);
                    }
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


        private List<TngouNewsModel.TngouBean> dataList = new ArrayList<>();


        public void addAll(List<TngouNewsModel.TngouBean> dataList) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        public void clear() {
            this.dataList.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tngou_new_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TngouNewsModel.TngouBean tngouEntity = dataList.get(position);
            holder.title.setText(tngouEntity.getTitle());
            holder.fromname.setText(tngouEntity.getFromname());
            holder.description.setText(tngouEntity.getDescription());
            ImageLoader.load(mActivity, AppConstants.API_SERVER_IMAGE_URL + tngouEntity.getImg(), holder.imageView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.imageView)
            ImageView imageView;
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.fromname)
            TextView fromname;
            @BindView(R.id.description)
            TextView description;

            public ViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TngouNewsModel.TngouBean tngouEntity = dataList.get(getLayoutPosition());
                        Intent intent = new Intent(mActivity, TngouNewsDetailActivity.class);
                        intent.putExtra(AppConstants.ID, tngouEntity.getId());
                        mActivity.startActivity(intent);
                    }
                });
            }
        }
    }
}
