package com.aswdc_projectname.myjokes.bal;

import android.database.Cursor;

import com.aswdc_projectname.myjokes.dal.DALCategory;
import com.aswdc_projectname.myjokes.model.BeanCategory;

import java.util.ArrayList;


public class BALCategory extends DALCategory {

    private static BALCategory instance;

    public static BALCategory getInstance() {
        if (instance == null) {
            instance = new BALCategory();
        }
        return instance;
    }

    public ArrayList<BeanCategory> selectAllCategory() {
        ArrayList<BeanCategory> categoryList = new ArrayList<>();
        Cursor cursor = getAllCategories();
        for (int i = 0; i < cursor.getCount(); i++) {
            BeanCategory bl = new BeanCategory();
            bl.setCategoryID(cursor.getInt(cursor.getColumnIndex(FIELD_CATEGORYID)));
            bl.setCategoryName(cursor.getString(cursor.getColumnIndex(FIELD_CATEGORYNAME)));
            categoryList.add(bl);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryList;
    }

}
