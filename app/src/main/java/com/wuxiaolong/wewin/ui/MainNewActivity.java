package com.wuxiaolong.wewin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.wuxiaolong.wewin.ui.myblog.MyBlogFragment;
import com.wuxiaolong.wewin.ui.set.SetActivity;
import com.wuxiaolong.wewin.ui.tngougirl.TngouGirlFragment;
import com.wuxiaolong.wewin.ui.tngounews.TngouNewsFragment;
import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页，侧滑菜单
 * 作者：吴小龙同學
 * github:https://github.com/WuXiaolong
 * 微信公众号：吴小龙同学
 */
public class MainNewActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        setTitle(getString(R.string.tngou_news));
        switchFragment(new TngouNewsFragment());
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), R.string.exit_text, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_tngou_girl) {
            switchFragment(new TngouGirlFragment());
        } else if (id == R.id.nav_my_blog) {
            switchFragment(new MyBlogFragment());
        } else if (id == R.id.nav_tngou_news) {
            switchFragment(new TngouNewsFragment());
        } else if (id == R.id.nav_set) {
            startActivity(new Intent(mActivity, SetActivity.class));
            return true;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换Fragment
     */

    public void switchFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, newFragment).commit();
    }


}
