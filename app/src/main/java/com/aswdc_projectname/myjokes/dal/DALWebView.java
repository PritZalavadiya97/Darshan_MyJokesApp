package com.aswdc_projectname.myjokes.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import static com.aswdc_projectname.myjokes.dal.DALTopic.FIELD_TOPICID;
import static com.aswdc_projectname.myjokes.dal.DALTopic.TABLE_TOPIC;

public class DALWebView extends DBHelper {

    protected Cursor getWebData(int topicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryCategory = "Select * from " + TABLE_TOPIC + " Where " + FIELD_TOPICID + " =?";
        Cursor cursor = db.rawQuery(queryCategory, new String[]{String.valueOf(topicID)});
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

}
