package com.xiaomolongstudio.wewin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.mvp.MainModel;
import com.xiaomolongstudio.wewin.ui.ShowImageActivity;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends BaseFragment {

    private MainModel mMainModel;
    private int position;
    private ShowImageActivity mActivity;
    @Bind(R.id.imageView)
    PhotoView mPhotoView;

    public static Fragment newFragment(MainModel mainModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mainList", mainModel);
        bundle.putInt("position", position);
        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (ShowImageActivity) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainModel = (MainModel) this.getArguments().getSerializable("mainList");
        position = this.getArguments().getInt("position", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String imageUrl = mMainModel.getIamgeUrl();
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView introduction = (TextView) view.findViewById(R.id.introduction);
        introduction.setText(mMainModel.getTitle());
        ViewCompat.setTransitionName(mPhotoView, imageUrl);
//        ViewCompat.setTransitionName(mPhotoView, AppConfig.TRANSIT_PIC);
//        ViewCompat.setTransitionName(introduction, mMainModel.getTitle());
        Picasso.with(getActivity()).load(imageUrl)
                .into(mPhotoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(mActivity)
                .load(mMainModel.getIamgeUrl())
                .into(mPhotoView);
    }


}
