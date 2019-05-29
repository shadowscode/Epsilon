package com.z2i.epsilon.Model;

public class Home {
    String domain;
    String icon;
    String pdf;
    String title;
    int credit;
    String surl;

    public Home() {
    }

    public Home(String domain, String icon, String pdf, String title, int credit, String surl) {
        this.domain = domain;
        this.icon = icon;
        this.pdf = pdf;
        this.title = title;
        this.credit = credit;
        this.surl = surl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}
