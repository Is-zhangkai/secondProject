package com.example.secondapp.Data;

import android.net.Uri;

import java.util.List;

public class News {


    /**
     * ######images : ["https://pic1.zhimg.com/v2-928c5f79297a8a22ed641c70be5604d8.jpg?source=8673f162"]
     * hint : 庄有猫 · 2 分钟阅读
     * ga_prefix : 111307
     * image_hue : 0x99887a
     * id : 9729911
     * title : 尿完尿为什么会打颤？
     * type : 0
     * url : https://daily.zhihu.com/story/9729911
     */
    private String images;
    private String hint;
    private String ga_prefix;
    private String image_hue;
    private int id;
    private String title;
    private int type;
    private String url;
    private String date;
    private List<News> list;
    private  String error;



    public void setImages(String images) {
        this.images = images;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setImage_hue(String image_hue) {
        this.image_hue = image_hue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

    public String getImages() {
        return images;
    }

    public String getHint() {
        return hint;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getImage_hue() {
        return image_hue;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public List<News> getList() {
        return list;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
