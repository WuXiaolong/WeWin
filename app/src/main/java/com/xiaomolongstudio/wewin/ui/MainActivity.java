package com.xiaomolongstudio.wewin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.fragment.MainFragment;
import com.xiaomolongstudio.wewin.utils.AppUtils;
import com.xiaomolongstudio.wewin.view.draglayout.DragLayout;
import com.xiaomolongstudio.wewin.view.draglayout.DragLayout.DragListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页，侧滑菜单
 *
 * @author 小尛龙
 *         github:https://github.com/WuXiaolong
 *         新浪微博：http://weibo.com/u/2175011601
 */
public class MainActivity extends BaseActivity {


    private String[] mPlanetTitles;
    @Bind(R.id.left_drawer)
    ListView mDrawerList;
    @Bind(R.id.dl)
    DragLayout mDragLayout;
    @Bind(R.id.main_title)
    ShimmerTextView main_title;
    @Bind(R.id.tv_set)
    TextView tv_set;
    @Bind(R.id.iv_icon)
    ImageView iv_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDragLayout();
        initView();
        initUmeng();
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

        //String device_token = UmengRegistrar.getRegistrationId(this);
        // Log.d("wxl", "device_token=" + device_token);
        AppUtils.getWeekAndDay(this);//邀请评论
    }

    private void initDragLayout() {
        mDragLayout.setmDragListener(new DragListener() {
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
        main_title.setReflectionColor(R.color.primary);
        Shimmer shimmer = new Shimmer();
        shimmer.start(main_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        iv_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mDragLayout.open();
            }
        });
        tv_set.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, SetActivity.class));
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
            selectItem(position);
        }
    }

    /**
     * 切换Fragment
     */
    private Fragment mCurrentFragment = new Fragment();

    public void switchFragment(Fragment newFragment, Fragment oldFragment, String url) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("url", url);
        newFragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame, newFragment).commit();
//        if (newFragment.isAdded()) {
//            Log.i("wxl", oldFragment + "isAdded");
//            fragmentTransaction.hide(mCurrentFragment).show(newFragment).commit();
//        } else {
//            Log.i("wxl", newFragment + "not isAdded");
//            fragmentTransaction.hide(mCurrentFragment).add(R.id.content_frame, newFragment).commit();
//        }
        mCurrentFragment = newFragment;
    }


    /**
     * 跳转页面
     *
     * @param position
     */
    private void selectItem(int position) {
        switch (position) {
            case 0:
//                switchFragment(AppConfig.mFragments[position], mCurrentFragment, "http://www.juzimi.com/meitumeiju?page=");
                switchFragment(new MainFragment(), mCurrentFragment, "http://www.juzimi.com/meitumeiju?page=");
                break;
            case 1:
                switchFragment(new MainFragment(), mCurrentFragment, "http://www.juzimi.com/meitumeiju/shouxiemeiju?page=");
                break;
            case 2:
                switchFragment(new MainFragment(), mCurrentFragment, "http://www.juzimi.com/meitumeiju/jingdianduibai?page=");
                break;
            default:

        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        // setTitle(mPlanetTitles[position]);
        main_title.setText(mPlanetTitles[position]);
        // mDrawerLayout.closeDrawer(mDrawerList);
        mDragLayout.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
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


    long exitTime = 0;

    @Override
    public void onBackPressed() {
//        if (imageDetailFragment.canBack()) {
//            imageDetailFragment.goBack();
//
//        } else {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(getApplicationContext(), R.string.exit_text, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
//            System.exit(0);
        }

    }


}
