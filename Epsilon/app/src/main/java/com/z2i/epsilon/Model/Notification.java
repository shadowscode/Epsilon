package com.z2i.epsilon.Model;

public class Notification {
    String title;
    String desc;
    String time;
    String date;
    String icon;

    public Notification() {
    }

    public Notification(String title, String desc, String time, String date, String icon) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.date = date;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
