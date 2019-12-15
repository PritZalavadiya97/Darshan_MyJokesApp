package com.aswdc_projectname.myjokes.bal;

import android.database.Cursor;

import com.aswdc_projectname.myjokes.dal.DALWebView;
import com.aswdc_projectname.myjokes.model.BeanTopics;

import static com.aswdc_projectname.myjokes.dal.DALTopic.FIELD_TOPICFAVORITE;
import static com.aswdc_projectname.myjokes.dal.DALTopic.FIELD_TOPICNAME;
import static com.aswdc_projectname.myjokes.dal.DALTopic.FIELD_TOPICWEB;

public class BALWebView extends DALWebView {

    static BALWebView instance;

    public static BALWebView getInstance() {
        if (instance == null) {
            instance = new BALWebView();
        }
        return instance;
    }

    public BeanTopics webData(int topicID) {
        Cursor cursor = getWebData(topicID);
        BeanTopics bl = new BeanTopics();
        bl.setTopicName(cursor.getString(cursor.getColumnIndex(FIELD_TOPICNAME)));
        bl.setTopicWEB(cursor.getString(cursor.getColumnIndex(FIELD_TOPICWEB)));
        bl.setIsFavorite(cursor.getInt(cursor.getColumnIndex(FIELD_TOPICFAVORITE)));
        cursor.close();
        return bl;
    }
}
