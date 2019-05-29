package com.z2i.epsilon.Model;

public class Request {
    String date;
    String time;
    String status;
    String title;
    String uid;
    String desc;
    String domain;
    Boolean likes;

    public Request() {
    }

    public Request(String date, String time, String status, String title, String domain, Boolean likes, String uid, String desc) {
        this.uid = uid;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.status = status;
        this.title = title;
        this.domain = domain;
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
