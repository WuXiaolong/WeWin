package com.wuxiaolong.wewin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wuxiaolong.wewin.draglayout.DragLayout;
import com.wuxiaolong.wewin.draglayout.DragLayout.DragListener;
import com.wuxiaolong.wewin.ui.juzimi.MainFragment;
import com.wuxiaolong.wewin.ui.set.SetActivity;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.wuxiaolong.wewin.utils.AppUtils;
import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页，侧滑菜单
 * 作者：吴小龙同學
 * github:https://github.com/WuXiaolong
 * 微信公众号：吴小龙同学
 */
public class MainActivity extends BaseActivity {


    private String[] mPlanetTitles;
    @BindView(R.id.left_drawer)
    ListView mDrawerList;
    @BindView(R.id.dl)
    DragLayout mDragLayout;
    @BindView(R.id.main_title)
    ShimmerTextView main_title;
    @BindView(R.id.tv_set)
    TextView tv_set;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDragLayout();
        initView();
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

    public void switchFragment(Fragment newFragment, int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putInt(AppConstants.POSITION, position);
        newFragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame, newFragment).commit();
    }


    /**
     * 跳转页面
     *
     * @param position
     */
    private void selectItem(int position) {
        switchFragment(new MainFragment(), position);

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        // setTitle(mPlanetTitles[position]);
        main_title.setText(mPlanetTitles[position]);
        // mDrawerLayout.closeDrawer(mDrawerList);
        mDragLayout.close();
    }


}
