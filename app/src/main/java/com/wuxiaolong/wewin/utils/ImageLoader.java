package com.wuxiaolong.wewin.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xiaomolongstudio.wewin.R;

import java.io.File;

/**
 * Created by WuXiaolong on 2016/8/16.
 */
public class ImageLoader {
    public static void load(Activity activity, String url, ImageView imageView) {
        load(activity, url, R.drawable.downloading, imageView);
    }

    public static void load(Activity activity, File file, ImageView imageView) {
        load(activity, file, R.drawable.downloading, imageView);
    }

    public static void load(Activity activity, Integer resourceId, ImageView imageView) {
        load(activity, resourceId, R.drawable.downloading, imageView);
    }

    public static void load(Activity activity, String url, int defaultImage, ImageView imageView) {
        Picasso.with(activity).load(url).placeholder(defaultImage).error(defaultImage).into(imageView);
    }

    public static void load(Activity activity, File file, int defaultImage, ImageView imageView) {
        Picasso.with(activity).load(file).placeholder(defaultImage).error(defaultImage).into(imageView);
    }

    public static void load(Activity activity, int resourceId, int defaultImage, ImageView imageView) {
        Picasso.with(activity).load(resourceId).placeholder(defaultImage).error(defaultImage).into(imageView);
    }
}
