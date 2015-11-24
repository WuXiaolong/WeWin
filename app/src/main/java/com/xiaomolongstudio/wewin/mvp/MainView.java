package com.xiaomolongstudio.wewin.mvp;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public interface MainView {
    void showData(List<MainModel> mainList);


    void hideProgress();
}
