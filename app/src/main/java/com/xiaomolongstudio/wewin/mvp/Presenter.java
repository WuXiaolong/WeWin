package com.xiaomolongstudio.wewin.mvp;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
