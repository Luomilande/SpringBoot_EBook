package com.example.springboot_ebook.model;

import java.util.Date;

public class Ebook {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiction_name() {
        return fiction_name;
    }

    public void setFiction_name(String fiction_name) {
        this.fiction_name = fiction_name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String fiction_name;//名字
    private Date createDate;//时间
    private String author;//章节名

    public int getChapter_content() {
        return chapter_content;
    }

    public void setChapter_content(int chapter_content) {
        this.chapter_content = chapter_content;
    }

    private int chapter_content;

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    private String synopsis;

    private String nodeName;
}
