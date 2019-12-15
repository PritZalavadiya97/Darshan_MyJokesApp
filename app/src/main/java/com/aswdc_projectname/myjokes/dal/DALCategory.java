package com.aswdc_projectname.myjokes.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DALCategory extends DBHelper {

    protected static final String FIELD_CATEGORYID = "CategoryID";
    protected static final String FIELD_CATEGORYNAME = "CategoryName";
    private static final String TABLE_CATEGORY = "Category";

    protected Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryCategory = "Select * from " + TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(queryCategory, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

}
