package com.wuxiaolong.wewin.ui.juzimi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wuxiaolong.wewin.model.MainModel;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.wuxiaolong.wewin.utils.AppUtils;
import com.xiaomolongstudio.wewin.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 吴小龙同學
 * on 2015/11/22.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<MainModel> mMainList = new ArrayList<>();
    private Activity activity;
    private boolean hasTitle;

    public RecyclerViewAdapter(Activity activity, boolean hasTitle) {
        this.activity = activity;
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
                .placeholder(R.drawable.downloading)
                .error(R.drawable.downloading)
                .into(holder.imageView);
        if (hasTitle) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(mMainList.get(position).getTitle());
        } else {
            holder.title.setVisibility(View.GONE);
        }
        holder.imageView.setTag(mMainList.get(position).getIamgeUrl());
        ViewCompat.setTransitionName(holder.imageView, mMainList.get(position).getIamgeUrl());
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bitmap bitmap = null;
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                            if (bitmapDrawable != null) {
                                bitmap = bitmapDrawable.getBitmap();
                            }
                            Intent intent = new Intent(activity, ShowImageActivity.class);
                            intent.putExtra("mainList", (Serializable) mMainList);
                            intent.putExtra("position", getLayoutPosition());
                            intent.putExtra(AppConstants.COLOR, AppUtils.getPaletteColor(bitmap));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                Pair<View, String> pair1 = Pair.create((View) imageView, mMainList.get(getLayoutPosition()).getIamgeUrl());
//                                Pair<View, String> pair2 = Pair.create((View) title, mMainList.get(getLayoutPosition()).getTitle());
//                                Log.d("wxl", "title===" + mMainList.get(getLayoutPosition()).getTitle());
//                                ActivityOptionsCompat options;
//                                options = ActivityOptionsCompat
//                                        .makeSceneTransitionAnimation(activity, pair1, pair2);
                                ActivityOptionsCompat options = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(activity, itemView, mMainList.get(getLayoutPosition()).getIamgeUrl());
//                                ActivityOptionsCompat options = ActivityOptionsCompat
//                                        .makeSceneTransitionAnimation(activity, itemView, AppConstants.TRANSIT_PIC);
                                ActivityCompat.startActivity(activity, intent, options.toBundle());
                            } else {
                                activity.startActivity(intent);
                            }

                        }
                    }

            );
        }
    }
}
