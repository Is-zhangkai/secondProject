package com.example.secondapp.Data;

import java.util.List;

public class Like {

    private List<Like> likes;
    private String images;
    private String hint;
    private int id;
    private String title;

    public void setImages(String images) {
        this.images = images;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }



    public List<Like> getLikes() {
        return likes;
    }

    public String getImages() {
        return images;
    }

    public String getHint() {
        return hint;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
