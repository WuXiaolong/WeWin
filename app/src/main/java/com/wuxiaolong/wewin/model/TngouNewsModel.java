package com.wuxiaolong.wewin.model;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/3.
 */

public class TngouNewsModel extends BaseModel {

    /**
     * count : 0
     * description : 一位开着手动挡的驾驶员说：“三秒钟完成挂挡、加油、提速的过程，正好可以锻炼技术，飙车通过，如果前面有排队的话，就悲催了
     * fcount : 0
     * fromname : 中华网
     * fromurl : http://news.china.com/socialgd/10000169/20161103/23844026.html
     * id : 14055
     * img : /top/161103/6fa28c1ab23f389df788ec56a46d21c3.jpg
     * keywords : 最短红绿灯3秒钟
     * rcount : 0
     * time : 1478141413000
     * title : 最短红绿灯只有3秒钟 飙车才可以通过(图)
     * topclass : 0
     */

    private List<TngouBean> tngou;

    public List<TngouBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<TngouBean> tngou) {
        this.tngou = tngou;
    }

    public static class TngouBean {
        private int count;
        private String description;
        private int fcount;
        private String fromname;
        private String fromurl;
        private int id;
        private String img;
        private String keywords;
        private int rcount;
        private long time;
        private String title;
        private int topclass;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public String getFromname() {
            return fromname;
        }

        public void setFromname(String fromname) {
            this.fromname = fromname;
        }

        public String getFromurl() {
            return fromurl;
        }

        public void setFromurl(String fromurl) {
            this.fromurl = fromurl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTopclass() {
            return topclass;
        }

        public void setTopclass(int topclass) {
            this.topclass = topclass;
        }
    }
}
