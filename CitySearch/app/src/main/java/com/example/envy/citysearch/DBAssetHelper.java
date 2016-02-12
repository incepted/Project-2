package com.example.envy.citysearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Envy on 2/8/2016.
 */

//Links to physical assets folder
public class DBAssetHelper extends SQLiteAssetHelper {

    public DBAssetHelper(Context context) {
        super(context, "places.db", null, 1);
    }
}
