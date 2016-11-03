package com.wuxiaolong.wewin.model;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/3.
 */

public class TngouGirlDetailModel {

    /**
     * count : 213
     * fcount : 0
     * galleryclass : 3
     * id : 997
     * img : /ext/161031/8e4063576efa51e1f92f444b0a322ac5.jpg
     * list : [{"gallery":997,"id":15250,"src":"/ext/161031/8e4063576efa51e1f92f444b0a322ac5.jpg"},{"gallery":997,"id":15251,"src":"/ext/161031/70ae430dcabc05cdb34f40bf63a77a3b.jpg"},{"gallery":997,"id":15252,"src":"/ext/161031/88b628f6f0c2efd98877f82bbd353e0c.jpg"},{"gallery":997,"id":15253,"src":"/ext/161031/46b707cbf09e3ae45eece2f1e722e5b0.jpg"},{"gallery":997,"id":15254,"src":"/ext/161031/c6c20d437fe8d403989b6cfee3b5d76f.jpg"},{"gallery":997,"id":15255,"src":"/ext/161031/f8a01d2669362e95827be280fb98ff50.jpg"},{"gallery":997,"id":15256,"src":"/ext/161031/39be3cc4a4a0f3d855a8f16500551416.jpg"},{"gallery":997,"id":15257,"src":"/ext/161031/4d721ec6ee136c47a3ab1517a4a643c6.jpg"},{"gallery":997,"id":15258,"src":"/ext/161031/6d594960e51e93895d6f21c091e4d328.jpg"},{"gallery":997,"id":15259,"src":"/ext/161031/1038b2200b02d094a2b8c4441a031c20.jpg"}]
     * rcount : 0
     * size : 10
     * status : true
     * time : 1477919562000
     * title : beautyleg红艳国韵旗袍美女性感高清美腿
     * url : http://www.tngou.net/tnfs/show/997
     */

    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String img;
    private int rcount;
    private int size;
    private boolean status;
    private long time;
    private String title;
    private String url;
    /**
     * gallery : 997
     * id : 15250
     * src : /ext/161031/8e4063576efa51e1f92f444b0a322ac5.jpg
     */

    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
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

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int gallery;
        private int id;
        private String src;

        public int getGallery() {
            return gallery;
        }

        public void setGallery(int gallery) {
            this.gallery = gallery;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
