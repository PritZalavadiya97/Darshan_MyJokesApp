package com.aswdc_projectname.myjokes.dal;

import com.aswdc_projectname.myjokes.AppController;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "myjokes.db";
    private static final int DB_VERSION = 1;

   public DBHelper() {
        super(AppController.getInstance(), DB_NAME, null, DB_VERSION);
    }
}
