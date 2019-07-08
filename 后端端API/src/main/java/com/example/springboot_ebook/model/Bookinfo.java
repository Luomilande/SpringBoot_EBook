package com.example.springboot_ebook.model;

import java.security.PrivateKey;
import java.util.Date;

public class Bookinfo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getWordnumber() {
        return wordnumber;
    }

    public void setWordnumber(String wordnumber) {
        this.wordnumber = wordnumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNewnode() {
        return newnode;
    }

    public void setNewnode(String newnode) {
        this.newnode = newnode;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String type) {
        this.booktype = type;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    private int id;//书的ID
    private String bookname;//书名
    private String author;//作者
    private String createDate;//时间
    private String synopsis;//简介
    private String nodeUrl;//章节url
    private String nodeName;//章节名
    private int node_id;//章节id
    private String imgurl;//图片路径

    private String wordnumber;//字数
    private String state;//状态
    private String newnode;//新章节
    private String booktype;//类型

}
