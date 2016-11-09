package com.wuxiaolong.wewin.ui.tngougirl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.wewin.ui.BaseFragment;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.wuxiaolong.wewin.utils.ImageLoader;
import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class TngouGirlDetailFragment extends BaseFragment {


    @BindView(R.id.photoView)
    PhotoView photoView;
    private String imageUrl;


    public static Fragment newFragment(String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.IMAGE_URL, imageUrl);
        TngouGirlDetailFragment fragment = new TngouGirlDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = this.getArguments().getString(AppConstants.IMAGE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tngou_girl_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setTransitionName(photoView, imageUrl);
        ImageLoader.load(mActivity, AppConstants.API_SERVER_IMAGE_URL + imageUrl, photoView);
    }
}
