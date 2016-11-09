package com.wuxiaolong.wewin.ui.tngougirl;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wuxiaolong.wewin.model.TngouGirlDetailModel;
import com.wuxiaolong.wewin.retrofit.RetrofitCallback;
import com.wuxiaolong.wewin.ui.BaseActivity;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.xiaomolongstudio.wewin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TngouGirlDetailActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<TngouGirlDetailModel.ListBean> tngouGirlDetailList;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tngou_girl_detail);
        ButterKnife.bind(this);
        id = this.getIntent().getIntExtra(AppConstants.ID, 0);
        setStatusBarColor();
        loadData();

    }


    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ActivityCompat.getColor(mActivity, R.color.color_black));
        }
    }

    private void loadData() {
        Call<TngouGirlDetailModel> call = apiStores.loadTngouGirlDetail(id);
        call.enqueue(new RetrofitCallback<TngouGirlDetailModel>() {
            @Override
            public void onSuccess(TngouGirlDetailModel model) {
                if (model.isStatus()) {
                    tngouGirlDetailList = model.getList();
//                    initToolbar(model.getTitle());
                    viewPager.setAdapter(new FragmentPagerAdapter());
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                toastShow(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                toastShow(t.getMessage());
            }

            @Override
            public void onFinish() {
            }
        });
        addCalls(call);
    }

    private class FragmentPagerAdapter extends FragmentStatePagerAdapter {

        public FragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return tngouGirlDetailList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return TngouGirlDetailFragment.newFragment(tngouGirlDetailList.get(position).getSrc());
        }

    }

}
