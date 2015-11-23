package com.xiaomolongstudio.wewin.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.mvp.MainModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;

public class ShowImageActivity extends AppCompatActivity {
    private List<MainModel> mMainList;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    int position;
    public static final String TRANSIT_PIC = "picture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS ); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.inject(this);
        mMainList = (List<MainModel>) this.getIntent().getSerializableExtra("mainList");
        position = this.getIntent().getIntExtra("position", 0);
        mViewPager.setPageTransformer(true, new CardTransformer(0.8f));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new ImageAdapter());
        mViewPager.setCurrentItem(position);
//        setEnterSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                MainModel mainModel = mMainList.get(mViewPager.getCurrentItem());
//                MainFragment fragment = (MainFragment) new ImageAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                sharedElements.clear();
////                sharedElements.put(mainModel.getIamgeUrl(), fragment.getSharedElement());
//            }
//        });
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

    private class ImageAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final String imageUrl = mMainList.get(position).getIamgeUrl();

            final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_detail_item, container, false);

            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            final TextView introduction = (TextView) view.findViewById(R.id.introduction);
            introduction.setText(mMainList.get(position).getTitle());
            final PhotoView imageView = (PhotoView) view.findViewById(R.id.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(imageUrl);
//                introduction.setTransitionName(mMainList.get(position).getTitle());
            }
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
