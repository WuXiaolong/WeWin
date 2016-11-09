package com.wuxiaolong.wewin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdViewListener;
import com.wuxiaolong.androidutils.library.LogUtil;
import com.xiaomolongstudio.wewin.R;

import org.json.JSONObject;


/**
 * Created by WuXiaolong
 * on 2016/10/25.
 */

public class AdView extends LinearLayout {
    private RelativeLayout adLayout;
    private RelativeLayout adViewLayout;
    private ImageView close;
    private com.baidu.mobads.AdView adView;
    private String adPlaceID = "3060845";

    public AdView(Context context) {
        super(context);
        initView(context);
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View view = View.inflate(context, R.layout.ad_layout, null);
        adLayout = (RelativeLayout) view.findViewById(R.id.adLayout);
        adViewLayout = (RelativeLayout) view.findViewById(R.id.adViewLayout);
        close = (ImageView) view.findViewById(R.id.close);
        // 创建广告View
        adView = new com.baidu.mobads.AdView(context, adPlaceID);
        // 设置监听器
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                LogUtil.d("wxl", "onAdSwitch");
            }


            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                LogUtil.d("wxl", "onAdShow " + info.toString());
                close.setVisibility(View.VISIBLE);
            }

            public void onAdReady(com.baidu.mobads.AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                LogUtil.d("", "onAdReady " + adView);

            }

            public void onAdFailed(String reason) {
                LogUtil.d("wxl", "onAdFailed " + reason);
                adLayout.setVisibility(View.GONE);
            }

            public void onAdClick(JSONObject info) {
                LogUtil.d("wxl", "onAdClick " + info.toString());
            }
        });

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adLayout.setVisibility(View.GONE);
            }
        });
        //将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        adViewLayout.addView(adView);
        addView(view);
    }
}
