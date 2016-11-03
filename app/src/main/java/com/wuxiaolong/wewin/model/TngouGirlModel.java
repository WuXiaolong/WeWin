package com.wuxiaolong.wewin.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WuXiaolong
 * on 2016/11/2.
 */

public class TngouGirlModel implements Serializable {

    /**
     * status : true
     * total : 983
     * tngou : [{"count":189,"fcount":0,"galleryclass":3,"id":997,"img":"/ext/161031/8e4063576efa51e1f92f444b0a322ac5.jpg","rcount":0,"size":10,"time":1477919562000,"title":"beautyleg红艳国韵旗袍美女性感高清美腿"},{"count":236,"fcount":0,"galleryclass":1,"id":996,"img":"/ext/161031/02b5547ab0f10831a4cec27995b3d793.jpg","rcount":0,"size":9,"time":1477919443000,"title":"都市极品巨乳女神李妍静私房妩媚动人性感"},{"count":520,"fcount":0,"galleryclass":3,"id":995,"img":"/ext/161027/c3f11f510ab9bc302739140cef619be6.jpg","rcount":0,"size":11,"time":1477575845000,"title":"OL白领美女秘书大胆肉丝美腿高跟迷人养眼写真"},{"count":332,"fcount":0,"galleryclass":5,"id":994,"img":"/ext/161027/6d4d0d7a02be668b4830904ad2c094f3.jpg","rcount":0,"size":8,"time":1477575744000,"title":"极品女秘书孙允珠大长腿性感美女酒店写真"},{"count":693,"fcount":0,"galleryclass":1,"id":993,"img":"/ext/161027/5b2b08f2e757d3de84fb4c1fa8e92f23.jpg","rcount":0,"size":6,"time":1477575688000,"title":"极品大胸美女腿模连体泳装大白腿美胸爆乳性感"},{"count":347,"fcount":0,"galleryclass":3,"id":992,"img":"/ext/161027/aa48f5ac5ce6e454efbe16cbfae4f617.jpg","rcount":0,"size":10,"time":1477575544000,"title":"大胆空姐LO制服诱惑巨乳喷血诱人黑丝美腿性感"},{"count":362,"fcount":0,"galleryclass":6,"id":991,"img":"/ext/161025/b2af6fd0a88d8979f8e957794c1ae270.jpg","rcount":0,"size":8,"time":1477400647000,"title":"模特美女白嫩美腿性感私房照图片"},{"count":340,"fcount":0,"galleryclass":3,"id":990,"img":"/ext/161025/e1bf044ccef5ce3e4e0b7abe37e5d7a8.jpg","rcount":0,"size":9,"time":1477400547000,"title":"大长腿美女性感蕾丝裙黑丝高跟美腿"},{"count":306,"fcount":0,"galleryclass":4,"id":989,"img":"/ext/161025/43a23ffb1b589354bcf479d6c9e6d032.jpg","rcount":0,"size":9,"time":1477400299000,"title":"气质嫩模蕾丝透视朦胧撩人性感"},{"count":679,"fcount":0,"galleryclass":5,"id":988,"img":"/ext/161022/adc5c851b1adafe140303b6d06dd4c00.jpg","rcount":0,"size":13,"time":1477137202000,"title":"风韵美女少妇文静美乳丝袜翘臀妖娆"}]
     */

    private boolean status;
    private int total;
    /**
     * count : 189
     * fcount : 0
     * galleryclass : 3
     * id : 997
     * img : /ext/161031/8e4063576efa51e1f92f444b0a322ac5.jpg
     * rcount : 0
     * size : 10
     * time : 1477919562000
     * title : beautyleg红艳国韵旗袍美女性感高清美腿
     */

    private List<TngouEntity> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TngouEntity> getTngou() {
        return tngou;
    }

    public void setTngou(List<TngouEntity> tngou) {
        this.tngou = tngou;
    }

    public static class TngouEntity implements Serializable{
        private int count;
        private int fcount;
        private int galleryclass;
        private int id;
        private String img;
        private int rcount;
        private int size;
        private long time;
        private String title;

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
    }
}
