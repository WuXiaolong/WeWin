package com.xiaomolongstudio.wewin.mvp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaomolongstudio.wewin.utils.AsyncHttpUtil;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class MainPresenter implements Presenter<MainView> {
    private MainView mMainView;
    private List<String> titleData;

    public void loadData(String url, final boolean hasTitle) {
        Log.d("wxl", "url=" + url);
        AsyncHttpUtil.get(url, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                try {
                    String doc = new String(response, "UTF-8");
                    Document mDocument = Jsoup.parse(doc);
                    if (hasTitle) {
                        titleData = new ArrayList<>();
                        Elements es = mDocument.getElementsByClass("xlistju");
                        for (Element e : es) {
                            titleData.add(e.text());
                        }
                    }
                    List<String> hrefData = new ArrayList<>();
                    Elements es1 = mDocument.getElementsByClass("chromeimg");
                    for (Element e : es1) {
                        hrefData.add(e.attr("src"));
                    }
                    List<MainModel> mainList = new ArrayList<>();
                    for (int i = 0; i < hrefData.size(); i++) {
                        MainModel mainModel = new MainModel();
                        if (hasTitle) {
                            mainModel.setTitle(titleData.get(i));
                        }
                        mainModel.setIamgeUrl(hrefData.get(i));
                        mainList.add(mainModel);
                    }
                    mMainView.showData(mainList);
                    mMainView.hideProgress();


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    mMainView.hideProgress();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                mMainView.hideProgress();
            }
        });


    }

    @Override
    public void attachView(MainView view) {
        this.mMainView = view;
    }

    @Override
    public void detachView() {
        this.mMainView = null;
    }
}
