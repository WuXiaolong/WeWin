package com.wuxiaolong.wewin.ui.tngougirl;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.wuxiaolong.wewin.model.TngouGirlModel;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.ui.BaseFragment;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.wuxiaolong.wewin.utils.ImageLoader;
import com.xiaomolongstudio.wewin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TngouGirlFragment extends BaseFragment {

    private DataAdapter dataAdapter;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private int page = 1;
    private int rows = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tngou_girl, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        page = new Random().nextInt(45);
        loadTngouGirl();
    }

    private void initView() {
        pullLoadMoreRecyclerView.setStaggeredGridLayout(2);//参数为列数
        pullLoadMoreRecyclerView.setRefreshing(true);
        dataAdapter = new DataAdapter();
        pullLoadMoreRecyclerView.setAdapter(dataAdapter);
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadTngouGirl();
            }

            @Override
            public void onLoadMore() {
                page = new Random().nextInt(45);
                loadTngouGirl();
            }
        });
    }

    private void loadTngouGirl() {
        Call<TngouGirlModel> call = apiStores.loadTngouGirl(page + 1, rows);
        call.enqueue(new RetrofitCallback<TngouGirlModel>() {
            @Override
            public void onSuccess(TngouGirlModel model) {
                if (model.isStatus()) {
                    if (page == 1) {
                        dataAdapter.clear();
                    }
                    dataAdapter.addAll(model.getTngou());
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

        private List<TngouGirlModel.TngouEntity> dataList = new ArrayList<>();


        public void addAll(List<TngouGirlModel.TngouEntity> dataList) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        public void clear() {
            this.dataList.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tngou_girl_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TngouGirlModel.TngouEntity tngouEntity = dataList.get(position);
            holder.title.setText(tngouEntity.getTitle());
            ImageLoader.load(mActivity, AppConstants.API_SERVER_IMAGE_URL + tngouEntity.getImg(), holder.imageView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.imgView)
            ImageView imageView;
            @BindView(R.id.title)
            TextView title;

            public ViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TngouGirlModel.TngouEntity tngouEntity = dataList.get(getLayoutPosition());
                        Intent intent = new Intent(mActivity, TngouGirlDetailActivity.class);
                        intent.putExtra(AppConstants.ID, tngouEntity.getId());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, itemView, tngouEntity.getImg());
                        ActivityCompat.startActivity(mActivity, intent, options.toBundle());
                    }
                });
            }
        }
    }
}
