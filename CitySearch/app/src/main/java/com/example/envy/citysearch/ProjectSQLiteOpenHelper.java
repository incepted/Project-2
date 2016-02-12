package com.example.envy.citysearch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Envy on 2/8/2016.
 */

//Connect to DB
public class ProjectSQLiteOpenHelper extends SQLiteOpenHelper {

    //Database Version declaration
    public static final int  DATABASE_VERSION = 1;
    //Link to database directory and file
    public static final String DATABASE_NAME = "places.db";

    public static final String TABLE_NAME = "places";
    public static final String ID = "_id";
    public static final String NAME = "NAME";
    public static final String ADDRESS = "ADDRESS";
    public static final String PRICE = "PRICE";
    public static final String FAVORITE = "FAVORITE";
    public static final String RATING = "RATING";
    public static final String CATEGORY = "CATEGORY";
    public static final String DESCRIPMINI = "DESCRIPMINI";
    public static final String DESCRIPFULL = "DESCRIPFULL";
    public static final String CREDITCARD = "CREDITCARD";
    public static final String SEARCH = "SEARCH";
    public static final String REVIEWS = "REVIEWS";
    public static final String PIC_ID = "PIC_ID";
    public static final String ICON_ID = "ICON_ID";


    //Create Array of table items
    public static final String[] COLUMN_NAMES = new String[] {ID, NAME, ADDRESS, PRICE, FAVORITE, RATING, CATEGORY, DESCRIPMINI, DESCRIPFULL, CREDITCARD, SEARCH, REVIEWS, PIC_ID, ICON_ID};

    public static final String SQL_DROP_PLACES_TABLE = " DROP TABLE IF EXISTS places ";

    //Constructor
    private ProjectSQLiteOpenHelper(Context context) {
        super(context, "places.db", null, 1);
    }

    public static ProjectSQLiteOpenHelper instance;
    public static ProjectSQLiteOpenHelper getInstance(Context context){
        if(instance == null){
            instance = new ProjectSQLiteOpenHelper(context.getApplicationContext());
        }return instance;
    }

    //onCreate code gen
    @Override
    public void onCreate(SQLiteDatabase db) {
        //use db.exeSQL to link with table items
        db.execSQL("CREATE TABLE places ( _id INTEGER PRIMARY KEY, NAME TEXT, ADDRESS TEXT, PRICE TEXT, FAVORITE TEXT, RATING TEXT, CATEGORY TEXT, DESCRIPMINI TEXT, DESCRIPFULL TEXT, CREDITCARD INTEGER, SEARCH TEXT, REVIEWS INTEGER, PIC_ID TEXT, ICON_ID TEXT ) ");
    }

    //onUpgrade code gen
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_PLACES_TABLE);
        onCreate(db);
    }

    //CURSOR, read data from db

    public Cursor getVenueList(){
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT * FROM places;
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);
        return cursor;
    }
    //Search function
    public Cursor mainSearchFunction(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = new String[]{ "%"+query+"%", "%"+query+"%", "%"+query+"%" };
        //SELECT * FROM places WHERE NAME LIKE '%coffee%' OR CATEGORY LIKE '%coffee%' OR SEARCH LIKE '%coffee%';
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, "NAME LIKE ? OR CATEGORY LIKE ? OR SEARCH LIKE ? ", selectionArgs, null, null, null, null);
        return cursor;
    }

    //Method to get venue by price
    public Cursor searchVenueByPrice(String price){
        SQLiteDatabase db = this.getReadableDatabase();

        //SELECT * FROM places WHERE PRICE = '$$';
        String[] DollazRating = new String[]{price};
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, "PRICE = ?", DollazRating, null, null, null, null);
        return cursor;
    }

    //Method to get specific keywords from db
    public Cursor resultsByCategory(String keyword){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selecionArgs = new String[]{ "%"+keyword+"%" };
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, "CATEGORY LIKE ?", selecionArgs, null, null, null);
        return cursor;
    }

    //Method to get "Trending" items by descending reviews of venue
    public Cursor resultsTrending(String trending){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES,null, null, null, null, "REVIEWS DESC");
        return cursor;
    }

    //Method to get 5 random database items
    public Cursor resultsRandom(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES,null,null,null,null,"RANDOM()","5");
        return cursor;
    }

    //Method to get favorited database items
    public Cursor resultsFavorited(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] favz = new String[]{"1"};
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, "FAVORITE = ?", favz, null, null, null, null );
        return cursor;
    }

    //Method to get 3 places for the "Suggestions" area for MainActivity
    public Cursor resultsRandomMain(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, "RANDOM()","3");
        return cursor;
    }

    public Cursor getDetailsById(int rowId){
        SQLiteDatabase db = this.getReadableDatabase();
        //valueOf method converts ints to String datatype.  TO match String datatypes here
        String[] selectionArgs = new String[]{ String.valueOf(rowId) };
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, "_id = ?", selectionArgs, null, null, null, null);
        return cursor;
    }

    public void updateFavoriteStatus(int currentFavoriteStatus, int rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        int changedFavorite = -1;
        if(currentFavoriteStatus == 0){
            changedFavorite = 1;
        }else if(currentFavoriteStatus == 1){
            changedFavorite = 0;
        }
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+FAVORITE+" = "+changedFavorite+" WHERE "+ID+" = "+rowId);
    }
}
