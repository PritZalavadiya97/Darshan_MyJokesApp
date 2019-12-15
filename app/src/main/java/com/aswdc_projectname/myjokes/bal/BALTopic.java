package com.aswdc_projectname.myjokes.bal;

import android.database.Cursor;

import com.aswdc_projectname.myjokes.dal.DALTopic;
import com.aswdc_projectname.myjokes.model.BeanTopics;

import java.util.ArrayList;

public class BALTopic extends DALTopic {

    private static BALTopic instance;

    public static BALTopic getInstance() {
        if (instance == null) {
            instance = new BALTopic();
        }
        return instance;
    }

    public ArrayList<BeanTopics> selectAllTopic(int categoryID) {
        ArrayList<BeanTopics> categoryList = new ArrayList<>();
        Cursor cursor = getAllTopic(categoryID);
        for (int i = 0; i < cursor.getCount(); i++) {
            BeanTopics bl = new BeanTopics();
            bl.setTopicID(cursor.getInt(cursor.getColumnIndex(FIELD_TOPICID)));
            bl.setTopicName(cursor.getString(cursor.getColumnIndex(FIELD_TOPICNAME)));
            bl.setTopicWEB(cursor.getString(cursor.getColumnIndex(FIELD_TOPICWEB)));
            bl.setIsFavorite(cursor.getInt(cursor.getColumnIndex(FIELD_TOPICFAVORITE)));
            categoryList.add(bl);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryList;
    }
    public ArrayList<BeanTopics> showFavorite() {
        ArrayList<BeanTopics> categoryList = new ArrayList<>();
        Cursor cursor = getAllFavorite();
        for (int i = 0; i < cursor.getCount(); i++) {
            BeanTopics bl = new BeanTopics();
            bl.setTopicID(cursor.getInt(cursor.getColumnIndex(FIELD_TOPICID)));
            bl.setTopicName(cursor.getString(cursor.getColumnIndex(FIELD_TOPICNAME)));
            bl.setTopicWEB(cursor.getString(cursor.getColumnIndex(FIELD_TOPICWEB)));
            bl.setIsFavorite(cursor.getInt(cursor.getColumnIndex(FIELD_TOPICFAVORITE)));
            categoryList.add(bl);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryList;
    }
}
