package com.wuxiaolong.wewin.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class MainModel implements Serializable {
    String title;
    String iamgeUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }
}
