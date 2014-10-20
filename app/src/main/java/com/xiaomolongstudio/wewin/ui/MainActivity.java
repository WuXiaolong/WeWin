package com.xiaomolongstudio.wewin.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adsmogo.adapters.AdsMogoCustomEventPlatformEnum;
import com.adsmogo.adview.AdsMogoLayout;
import com.adsmogo.controller.listener.AdsMogoListener;
import com.adsmogo.util.AdsMogoSize;
import com.nineoldandroids.view.ViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.update.UmengUpdateAgent;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.fragment.ImageDetailFragment;
import com.xiaomolongstudio.wewin.utils.AppConfig;
import com.xiaomolongstudio.wewin.utils.dragLayout.DragLayout;
import com.xiaomolongstudio.wewin.utils.dragLayout.DragLayout.DragListener;

/**
 * 首页，侧滑菜单
 *
 * @author 小尛龙
 */
public class MainActivity extends BaseActivity {
    private ListView mDrawerList;

    private CharSequence mTitle;
    private String[] mPlanetTitles;

    private DragLayout dragLayout;
    private TextView main_title, tv_set;
    private ImageView iv_icon;
    private ImageDetailFragment imageDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDragLayout();
        initView();
        initMogo();
        initUmeng();
        initImageDetail();

    }

    /**
     * 图片详情
     */
    private void initImageDetail() {
        imageDetailFragment = (ImageDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_image_fragment);
        getSupportFragmentManager().beginTransaction()
                .hide(imageDetailFragment).commit();
    }

    private void initUmeng() {
        // 用户反馈
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();
        // 自动更新
        UmengUpdateAgent.update(this);
        // 推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();

        String device_token = UmengRegistrar.getRegistrationId(this);
//        Log.d("wxl", "device_token=" + device_token);

    }

    private void initDragLayout() {
        dragLayout = (DragLayout) findViewById(R.id.dl);
        dragLayout.setDragListener(new DragListener() {
            public void onOpen() {
                // mDrawerList.smoothScrollToPosition(new Random().nextInt(30));
            }

            public void onClose() {
                shake();
            }

            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_icon, 1 - percent);
            }
        });
    }

    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        main_title = (TextView) findViewById(R.id.main_title);
        tv_set = (TextView) findViewById(R.id.tv_set);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        iv_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                dragLayout.open();
            }
        });
        tv_set.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            }
        });
        selectItem(0);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(0);
        }
    }

    /**
     * 切换Fragment
     */
    private Fragment mCurrentFragment = new Fragment();

    public void switchFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (newFragment.isAdded()) {
            // Log.i("wxl", oldFragment + "isAdded");
            fragmentTransaction.hide(oldFragment).show(newFragment).commit();
        } else {
            // Log.i("wxl", newFragment + "not isAdded");
            fragmentTransaction.hide(oldFragment)
                    .add(R.id.content_frame, newFragment).commit();
        }
        mCurrentFragment = newFragment;
    }

    public void showImageFragment(boolean show, String imgTxt, String imgUrl) {
        // showActionbarWithTabs(!show);
        if (show) {
            getSupportFragmentManager().beginTransaction()
                    .show(imageDetailFragment).commit();
            imageDetailFragment.setImgData(imgTxt, imgUrl);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(imageDetailFragment).commit();
        }

    }


    /**
     * 跳转页面
     *
     * @param position
     */
    private void selectItem(int position) {
        switchFragment(AppConfig.mFragments[position], mCurrentFragment);
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        // setTitle(mPlanetTitles[position]);
        main_title.setText(mPlanetTitles[position]);
        // mDrawerLayout.closeDrawer(mDrawerList);
        dragLayout.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void onDestroy() {
        AdsMogoLayout.clear();
        // 清除 adsMogoLayout 实例 所产生用于多线程缓冲机制的线程池
        // 此方法请不要轻易调用，如果调用时间不当，会造成无法统计计数
        // adsMogoLayoutCode.clearThread();
        super.onDestroy();
    }

    long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (imageDetailFragment.canBack()) {
            imageDetailFragment.goBack();

        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

        }
    }

    private RelativeLayout adViewLayout, adLayout;
    private ImageView adClose;
    private Handler handler = null;

    private void initMogo() {
        // 创建属于主线程的handler
        handler = new Handler();
        adViewLayout = (RelativeLayout) findViewById(R.id.adViewLayout);
        adLayout = (RelativeLayout) findViewById(R.id.adLayout);
        adClose = (ImageView) findViewById(R.id.adClose);
        // 设置请求广告的尺寸大小
        AdsMogoLayout adsMogoLayoutCode = new AdsMogoLayout(this,
                AppConfig.KEY_AD_MOGO, AdsMogoSize.AdsMogoAutomaticScreen);
        adsMogoLayoutCode.setAdsMogoListener(new AdsMogoListener() {

            @Override
            public void onRequestAd(String arg0) {
//                Log.d("wxl", "onRequestAd");
            }

            @Override
            public void onReceiveAd(ViewGroup arg0, String arg1) {

//                Log.d("wxl", "onReceiveAd");
                new Thread() {
                    public void run() {
                        handler.post(runnableUi);
                    }
                }.start();
            }

            @Override
            public void onRealClickAd() {
//                Log.d("wxl", "onRealClickAd");

            }

            @Override
            public void onInitFinish() {
//                Log.d("wxl", "onInitFinish");

            }

            @Override
            public void onFailedReceiveAd() {
//                Log.d("wxl", "onFailedReceiveAd");
                adLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCloseMogoDialog() {
//                Log.d("wxl", "onCloseMogoDialog");
            }

            @Override
            public boolean onCloseAd() {
//                Log.d("wxl", "onCloseAd");
                return false;
            }

            @Override
            public void onClickAd(String arg0) {
                Log.d("wxl", "onClickAd");
            }

            @Override
            public Class<?> getCustomEvemtPlatformAdapterClass(
                    AdsMogoCustomEventPlatformEnum arg0) {
                return null;
            }
        });
        // 如果该平台无设置的广告尺寸或当前屏幕无法展示该尺寸大小的广告，isOtherSizes为true时
        // 将请求其他尺寸广告，为false时则认为该广告请求失败
        adsMogoLayoutCode.isOtherSizes = true;
        adViewLayout.addView(adsMogoLayoutCode);
        adClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                adLayout.setVisibility(View.GONE);

            }

        });
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            // 更新界面
            adLayout.setVisibility(View.VISIBLE);
        }

    };
}
