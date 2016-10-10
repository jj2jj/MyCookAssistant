package com.example.jessica0906zjj.mycookassistant;

public class MyDomain {
    private String id; // id
    private String date; // 日期
    private String title; // 标题
    private String topicFrom; //选题来自
    private String topic; // 选题
    private String imgUrl; // 图片url
    private String targetUrl; // 目标url
    private int width; // 宽
    private int height; // 高
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicFrom() {
        return topicFrom;
    }

    public void setTopicFrom(String topicFrom) {
        this.topicFrom = topicFrom;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
