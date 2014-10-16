package com.xiaomolongstudio.wewin.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaomolongstudio.wewin.R;

/**
 * 审核区
 * 
 * @author 小尛龙
 * 
 */
@SuppressLint("InflateParams")
public class MainFragment extends Fragment {
	public static MainFragment mainFragment = null;

	public static MainFragment newInstance() {
		if (mainFragment == null) {
			mainFragment = new MainFragment();
		}
		return mainFragment;
	}

	private View mView;
	private TextView introduction;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.main_fragment, null);

		initView();

		return mView;
	}

	private void initView() {

		introduction = (TextView) mView.findViewById(R.id.introduction);
		introduction.setText(Html.fromHtml(getResources().getString(
				R.string.introduction)));
	}

}
