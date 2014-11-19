package com.xiaomolongstudio.wewin.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomolongstudio.wewin.R;

import java.util.Random;

/**
 * Created by 小尛龙 on 2014/10/19.
 */
public class MyBaseAdapter extends BaseAdapter {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    private static final int[] COLORS = {R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_purple_light, R.color.holo_red_light};

    public MyBaseAdapter(Activity activity) {
        init(activity);
    }

    private void init(Activity activity) {
        if (options == null) {
            Resources mResource;
            Random random = new Random();
            Drawable mDefaultImageDrawable = new ColorDrawable(activity.getResources().getColor(COLORS[random.nextInt(COLORS.length)]));
            int RandomColor = activity.getResources().getColor(COLORS[random.nextInt(COLORS.length)]);
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.detail_content_temp_icon)
                    .showImageOnFail(R.drawable.detail_content_temp_icon).cacheInMemory(true)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
