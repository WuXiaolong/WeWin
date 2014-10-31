package com.xiaomolongstudio.wewin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaomolongstudio.wewin.R;
import com.xiaomolongstudio.wewin.adapter.MyAdapter;
import com.xiaomolongstudio.wewin.ui.MainActivity;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 美图美句
 *
 * @author 小尛龙
 */
public class MainFragment extends Fragment implements
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private View mView;
    private List<String> titleData;
    private List<String> hrefData;
    private List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data;
    private Map<String, Object> map;
    private StaggeredGridView mGridView;
    private MyAdapter mAdapter = null;
    private boolean mHasRequestedMore = false;
    private int mPage = 1;
    private String url = "http://www.juzimi.com/meitumeiju?page=";
    private String mUrl;
    private boolean isFirst = true;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");

            if (url.equals("http://www.juzimi.com/meitumeiju?page=")) {
                isFirst = true;
            } else {
                isFirst = false;
            }
            Random random = new Random();
            mPage = random.nextInt(29);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_fragment, null);
        ButterKnife.inject(this, mView);
        mGridView = (StaggeredGridView) mView.findViewById(R.id.grid_view);
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);
        getString();
        return mView;
    }

    /**
     * 请求String类型
     */
    private Document mDocument;

    public void getString() {
        if (mHasRequestedMore) {
            mPage = mPage + 1;

        } else {
//            mPage = 1;
        }
        mUrl = url + String.valueOf(mPage);
//        Log.d("wxl", "mUrl=" + mUrl);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mUrl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                progressBar.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
                try {
                    mHasRequestedMore = false;
                    String doc = new String(response, "UTF-8");
                    if (doc.equals("null")) {
                        Toast.makeText(getActivity(), "网络不给力",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    mDocument = Jsoup.parse(doc);
                    if (isFirst) {
                        titleData = new ArrayList<String>();
                        Elements es = mDocument.getElementsByClass("xlistju");
                        for (Element e : es) {
                            titleData.add(e.text());
                        }
                    }
                    hrefData = new ArrayList<String>();
                    Elements es1 = mDocument.getElementsByClass("chromeimg");
                    for (Element e : es1) {
                        hrefData.add(e.attr("src"));
                    }
                    data = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < hrefData.size(); i++) {
                        map = new HashMap<String, Object>();
                        if (isFirst) {
                            map.put("title", titleData.get(i));
                        }
                        map.put("imgUrl", hrefData.get(i));
                        data.add(map);
                    }
                    mData.addAll(data);
                    if (mAdapter == null) {
                        mAdapter = new MyAdapter(getActivity(), data, isFirst);
                        mGridView.setAdapter(mAdapter);
                    } else {
                        mAdapter.getmData().addAll(data);
                        mAdapter.notifyDataSetChanged();
                    }


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
//                Log.d("wxl", "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                getString();
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view,
                            int position, long id) {
        if (isFirst) {
            ((MainActivity) getActivity()).showImageFragment(true, mData.get(position).get("title").toString(), mData.get(position).get("imgUrl").toString());
        } else {
            ((MainActivity) getActivity()).showImageFragment(true, "", mData.get(position).get("imgUrl").toString());
        }

    }
}
