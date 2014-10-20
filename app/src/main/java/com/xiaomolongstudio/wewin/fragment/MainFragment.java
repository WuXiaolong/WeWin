package com.xiaomolongstudio.wewin.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaomolongstudio.wewin.R;

/**
 * 剩友圈
 *
 * @author 小尛龙
 */
public class MainFragment extends Fragment {


    private View mView;
    private TextView introduction;

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
//    public static MainFragment mainFragment = null;
//
//    public static MainFragment newInstance() {
//        if (mainFragment == null) {
//            mainFragment = new MainFragment();
//        }
//        return mainFragment;
//    }
}
