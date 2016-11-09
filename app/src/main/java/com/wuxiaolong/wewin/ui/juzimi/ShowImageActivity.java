package com.wuxiaolong.wewin.ui.juzimi;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.wuxiaolong.wewin.model.MainModel;
import com.wuxiaolong.wewin.ui.BaseActivity;
import com.xiaomolongstudio.wewin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImageActivity extends BaseActivity {
    private List<MainModel> mMainList;
    @BindView(R.id.viewPager)
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


}
