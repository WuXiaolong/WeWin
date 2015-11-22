package com.xiaomolongstudio.wewin.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.mvp.MainModel;
import com.xiaomolongstudio.wewin.ui.ShowImageActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<MainModel> mMainList;
    private Activity activity;
    private boolean hasTitle;

    public RecyclerViewAdapter(Activity activity, List<MainModel> mainList, boolean hasTitle) {
        this.activity = activity;
        this.mMainList = mainList;
        this.hasTitle = hasTitle;
    }

    public List<MainModel> getmMainList() {
        return mMainList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recycler_view_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(activity)
                .load(mMainList.get(position).getIamgeUrl())
                .placeholder(R.drawable.detail_content_temp_icon)
                .error(R.drawable.detail_content_temp_icon)
                .into(holder.imageView);
        if (hasTitle) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(mMainList.get(position).getTitle());
        } else {
            holder.title.setVisibility(View.GONE);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            holder.imageView.setTransitionName("imageshow" + position);
//            holder.title.setTransitionName("title" + position);
//        }
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imgView)
        ImageView imageView;
        @InjectView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ShowImageActivity.class);
                    intent.putExtra("mainList", (Serializable) mMainList);
                    intent.putExtra("position", getLayoutPosition());
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        Pair<View, String> pair1 = Pair.create((View) imageView, "imageshow" + getLayoutPosition());
//                        Pair<View, String> pair2 = Pair.create((View) title, "title" + getLayoutPosition());
//                        ActivityOptionsCompat options = ActivityOptionsCompat
//                                .makeSceneTransitionAnimation(activity, pair1, pair2);
//                        activity.startActivity(intent, options.toBundle());
//                    } else
//
//                    {
                        activity.startActivity(intent);
                    }

//                }
            });
        }
    }
}
