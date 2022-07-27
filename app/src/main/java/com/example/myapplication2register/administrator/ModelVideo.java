package com.example.myapplication2register.administrator;

public class ModelVideo {
    String videolink;
    String title;
    String id;
    String timestamp;

    //construtor
    public ModelVideo() {
        //firebase requires empty constructir
    }

    public ModelVideo(String videolink, String title, String id, String timestamp) {
        this.videolink = videolink;
        this.title = title;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
