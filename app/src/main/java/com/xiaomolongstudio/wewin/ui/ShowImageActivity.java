package com.xiaomolongstudio.wewin.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.fragment.ShowImageFragment;
import com.xiaomolongstudio.wewin.mvp.MainModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ShowImageActivity extends BaseActivity {
    private List<MainModel> mMainList;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private int position;
    private int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);
        mMainList = (List<MainModel>) this.getIntent().getSerializableExtra("mainList");
        position = this.getIntent().getIntExtra("position", 0);
        color = this.getIntent().getIntExtra("color", getResources().getColor(R.color.red));
        setStatusBarColor(color);
        mViewPager.setPageTransformer(true, new CardTransformer(0.8f));
        mViewPager.setAdapter(new FragmentPagerAdapter());
        mViewPager.setCurrentItem(position);
//        setEnterSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                MainModel mainModel = mMainList.get(mViewPager.getCurrentItem());
//                ShowImageFragment fragment = (ShowImageFragment) new FragmentPagerAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                sharedElements.clear();
//                sharedElements.put(mainModel.getIamgeUrl(), fragment.getSharedElement());
//            }
//        });
    }

    public void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(statusBarColor);
        }
    }

    /**
     * Awesome Launcher-inspired page transformer
     */
    private static class CardTransformer implements ViewPager.PageTransformer {

        private final float scaleAmount;

        public CardTransformer(float scalingStart) {
            scaleAmount = 1 - scalingStart;
        }

        @Override
        public void transformPage(View page, float position) {
            if (position >= 0f) {
                final int w = page.getWidth();
                float scaleFactor = 1 - scaleAmount * position;

                page.setAlpha(1f - position);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setTranslationX(w * (1 - position) - w);
            }
        }

    }

    private class FragmentPagerAdapter extends FragmentStatePagerAdapter {

        public FragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return mMainList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ShowImageFragment.newFragment(mMainList.get(position), position);
        }

    }

    private class ImageAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final String imageUrl = mMainList.get(position).getIamgeUrl();
            final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_detail_item, container, false);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            final TextView introduction = (TextView) view.findViewById(R.id.introduction);
            introduction.setText(mMainList.get(position).getTitle());
            final PhotoView imageView = (PhotoView) view.findViewById(R.id.imageView);
            ViewCompat.setTransitionName(imageView, mMainList.get(position).getTitle());
//            ViewCompat.setTransitionName(introduction, mMainList.get(position).getTitle());
            Picasso.with(getApplicationContext()).load(imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mMainList != null ? mMainList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
