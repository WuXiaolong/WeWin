package com.wuxiaolong.wewin.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xiaomolongstudio.wewin.R;

/**
 * 欢迎页
 */
public class WelcomeActivity extends Activity {
    TextView appName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        appName = (TextView) findViewById(R.id.appName);
        Animation mAnimation;
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        appName.setAnimation(mAnimation);
        mAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                intoMain();
            }
        });

    }


    /**
     * 进入首页
     */
    private void intoMain() {
        startActivity(new Intent(WelcomeActivity.this, MainNewActivity.class));
        overridePendingTransition(R.anim.in_anim, R.anim.out_anim);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
