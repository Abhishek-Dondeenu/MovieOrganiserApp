package com.example.movieorganiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Movies.db";
    public static final String TABLE_NAME = "Movies_table";
    public static final String COL_0="id";
    public static final String COL_1="Movie_Title";
    public static final String COL_2="Year";
    public static final String COL_3="Director";
    public static final String COL_4="Movie_Cast";
    public static final String COL_5="Rating";
    public static final String COL_6="Review";
    public static final String COL_7 = "Favourite_Status";
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, Movie_Title TEXT , Year INTEGER, Director TEXT , Movie_Cast  TEXT , Rating INTEGER, Review TEXT, Favourite_Status TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    //a database helper method that inserts data into database
    public boolean insertData(String title,int y,String d,String c,int ra, String re,String f){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,y);
        contentValues.put(COL_3,d);
        contentValues.put(COL_4,c);
        contentValues.put(COL_5,ra);
        contentValues.put(COL_6,re);
        contentValues.put(COL_7,f);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    //getMovieTitles method
    public Cursor getMovieTitles(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT Movie_Title FROM "+ TABLE_NAME,null);
        return res;
    }
    //getAllMovieData method
    public Cursor getAllMovieData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return res;
    }
    //getFavouriteMovieTitles method
    public Cursor getFavouriteMovieTitles(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT "+ COL_1 +" FROM "+ TABLE_NAME + " WHERE " + COL_7 + " = 'favourite' ",null);
        return res;
    }
    //updateFavouritesColumn method
    public boolean updateFavouritesColumn(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("UPDATE "+ TABLE_NAME +" SET "+ COL_7 + " = 'favourite' " + " WHERE " + COL_1 + "=" + "'" + title + "'" ,null);
        return cursor.getCount() == 0;
    }
    //deselectFavouritesColumn method
    public boolean deselectFavouritesColumn(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("UPDATE "+ TABLE_NAME +" SET "+ COL_7 + " = 'not favourite'" + " WHERE " + COL_1 + "=" + "'" + title + "'" ,null);
        return cursor.getCount() == 0;
    }
    //getMovieData method
    public Cursor getMovieData(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE " + COL_1 + "=" + "'" + title + "'",null);
        return res;
    }
    //getMovieTitles method
    public boolean updateData(String title,int y,String d,String c,int ra, String re,String f){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,y);
        contentValues.put(COL_3,d);
        contentValues.put(COL_4,c);
        contentValues.put(COL_5,ra);
        contentValues.put(COL_6,re);
        contentValues.put(COL_7,f);
        db.update(TABLE_NAME,contentValues,"Movie_Title=?",new String[]{title});
        return true;

    }

}
