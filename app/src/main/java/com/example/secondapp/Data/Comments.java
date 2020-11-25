package com.example.secondapp.Data;

import java.io.Serializable;

public class Comments implements Serializable {
    /**
     * author : 毛毛虫的外衣
     * time : 1605403384
     * avatar : https://pic2.zhimg.com/v2-59a6813f6bad837f0c4f248917faca87_l.jpg?source=8673f162
     * comment_id : 33750689
     * content : 隔屏心疼，朴实的爱情。我不知道每个人对浪漫的理解，浪漫是气氛的沉浸，是幻想的曼妙，是生活暂时的无瑕。
     * likes : 2
     */
    private String author;
    private String time;
    private String avatar;
    private int comment_id;
    private String content;
    private int likes;
    private String number;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getComment_id() {
        return comment_id;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public String getNumber() {
        return number;
    }
}
