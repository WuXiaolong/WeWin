package com.wuxiaolong.wewin.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帮助说明
 * 
 * @author 小尛龙
 * 
 */
public class HelpActivity extends BaseActivity {
    @BindView(R.id.introduction)
    TextView introduction;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
        ButterKnife.bind(this);
		initToolbar(getString(R.string.circle));
        introduction.setText(Html.fromHtml(getResources().getString(
                R.string.introduction)));

    }

}
