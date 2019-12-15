package com.aswdc_projectname.myjokes.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DALTopic extends DBHelper {

    public static final String FIELD_TOPICNAME = "TopicName";
    public static final String FIELD_TOPICWEB = "TopicWEB";
    public static final String FIELD_TOPICFAVORITE = "isFavorite";
    protected static final String FIELD_TOPICID = "TopicID";
    static final String TABLE_TOPIC = "Topic";
    private static final String FIELD_CATEGORY_ID = "CategoryID";

    protected Cursor getAllTopic(int categoryID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryCategory = "Select * from " + TABLE_TOPIC + " Where " + FIELD_CATEGORY_ID + " = " + categoryID;
        Cursor cursor = db.rawQuery(queryCategory, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    protected Cursor getAllFavorite() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryCategory = "Select * from " + TABLE_TOPIC + " Where " + FIELD_TOPICFAVORITE + " = 1";
        Cursor cursor = db.rawQuery(queryCategory, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public void updateFavorite(int id, int fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryFavorite = "Update " + TABLE_TOPIC + " set " + FIELD_TOPICFAVORITE + " = " + fav + " Where " + FIELD_TOPICID + " = " + id;
        db.execSQL(queryFavorite);
        db.close();

    }
}
