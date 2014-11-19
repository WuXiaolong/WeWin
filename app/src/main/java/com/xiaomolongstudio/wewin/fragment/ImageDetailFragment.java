package com.xiaomolongstudio.wewin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.ui.MainActivity;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 大图展示
 *
 * @author 小尛龙
 */
public class ImageDetailFragment extends Fragment {
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
                    .showImageForEmptyUri(R.drawable.detail_content_temp_icon)
                    .showImageOnFail(R.drawable.detail_content_temp_icon).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(false)
                    .build();
        }
    }


    public void setImgData(String imgTxt, String imgUrl) {
        //Log.v("wxl", "imgTxt=" + imgTxt);
        PhotoView show_image = (PhotoView) mView.findViewById(R.id.show_image);
        mView.setVisibility(View.VISIBLE);
        show_text.setText(imgTxt);

        imageLoader.displayImage(imgUrl,
                show_image, options);
        mAttacher = new PhotoViewAttacher(show_image);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                goBack();
            }
        });
    }

    public boolean canBack() {
        return mView.getVisibility() == View.VISIBLE;
    }

    public void goBack() {
        if (mAttacher != null) {
            mAttacher.cleanup();
        }
        mView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).showImageFragment(false, null, null);
    }
}
