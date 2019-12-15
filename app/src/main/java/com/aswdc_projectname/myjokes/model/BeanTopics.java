package com.aswdc_projectname.myjokes.model;

public class BeanTopics {

    int topicID;
    String topicName;
    String topicWEB;
    int categoryID;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    private int isFavorite;

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicWEB() {
        return topicWEB;
    }

    public void setTopicWEB(String topicWEB) {
        this.topicWEB = topicWEB;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
