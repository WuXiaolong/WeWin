package com.xiaomolongstudio.wewin.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.ui.MainActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 */
public class ImageDetailFragment extends Fragment {
    private ImageView show_image;
    private TextView show_text;
    private View mView;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    private PhotoViewAttacher mAttacher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mView.setVisibility(View.INVISIBLE);
        show_image = (ImageView) mView.findViewById(R.id.show_image);
        show_text = (TextView) mView.findViewById(R.id.show_text);
        initOptions();
        mView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return mView;
    }


    private void initOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        }
    }


    public void setImgData(String imgTxt, String imgUrl) {
        Log.v("wxl", "imgTxt=" + imgTxt);
        mView.setVisibility(View.VISIBLE);
        show_text.setText(imgTxt);

        imageLoader.displayImage(imgUrl,
                show_image, options);
        mAttacher = new PhotoViewAttacher(show_image);
    }

    public boolean canBack() {
        return mView.getVisibility() == View.VISIBLE;
    }

    public void goBack() {
        mView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).showImageFragment(false, null, null);
    }
}
