package com.wuxiaolong.wewin.model;

/**
 * Created by Administrator
 * on 2016/11/3.
 */

public class BaseModel {
    private boolean status;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
