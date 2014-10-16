package com.xiaomolongstudio.wewin.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.fragment.MainFragment;
import com.xiaomolongstudio.wewin.utils.dragLayout.DragLayout;
import com.xiaomolongstudio.wewin.utils.dragLayout.DragLayout.DragListener;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    // private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Fragment[] mFragments = { MainFragment.newInstance()
             };
    public static MenuItem action_overflow = null, action_msg;
    private DragLayout dragLayout;
    private GridView gv_img;
    private ListView lv;
    private TextView main_title;
    private ImageView iv_icon, iv_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDragLayout();
        initView();
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
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        // adapter = new ImageAdapter(this);
        // gv_img.setAdapter(adapter);
        // gv_img.setOnItemClickListener(new OnItemClickListener() {
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view,
        // int position, long id) {
        // Intent intent = new Intent(MainActivity.this,
        // ImageActivity.class);
        // intent.putExtra("path", adapter.getItem(position));
        // startActivity(intent);
        // }
        // });
        // lv = (ListView) findViewById(R.id.lv);
        // lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
        // R.layout.item_text, new String[] { "NewBee", "ViCi Gaming",
        // "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
        // "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
        // "Titan", "Mousesports", "Fnatic", "Team Liquid",
        // "MVP Phoenix", "NewBee", "ViCi Gaming",
        // "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
        // "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
        // "Titan", "Mousesports", "Fnatic", "Team Liquid",
        // "MVP Phoenix" }));
        // lv.setOnItemClickListener(new OnItemClickListener() {
        // @Override
        // public void onItemClick(AdapterView<?> arg0, View arg1,
        // int position, long arg3) {
        // Util.t(getApplicationContext(), "click " + position);
        // }
        // });
        iv_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                dragLayout.open();
            }
        });
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

    private Fragment mCurrentFragment = new Fragment();

    public void switchFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager()
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

    private void selectItem(int position) {
        switch (position) {
            case 0:
                switchFragment(mFragments[0], mCurrentFragment);
                break;
            case 1:
                //switchFragment(ExamineFragment.newInstance(), mCurrentFragment);
                break;
            case 2:
                //switchFragment(VideoFragment.newInstance(), mCurrentFragment);
                break;

            default:
                break;
        }
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        // setTitle(mPlanetTitles[position]);
        main_title.setText(mPlanetTitles[position]);
        // mDrawerLayout.closeDrawer(mDrawerList);
        dragLayout.close();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
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
}
