package com.example.springboot_ebook.model;

import java.util.Date;

public class Nodeinfo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodedate() {
        return nodedate;
    }

    public void setNodedate(String nodedate) {
        this.nodedate = nodedate;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    private int id;
    private int bookid;//书的id
    private String nodeName;//章节名
    private String nodedate;//时间
    private String txt;
    private String imgurl;//图片路径
}
