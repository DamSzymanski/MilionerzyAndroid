package com.example.milionerzyzaliczenie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper  extends SQLiteOpenHelper implements BaseColumns {

   //podbijać przy zmianie
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "Questions";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

        public static final String TABLE_NAME = "questions";
        public static final String Question = "question";
        public static final String A = "a";
        public static final String B = "b";
        public static final String C= "c";
        public static final String D= "d";
        public static final String Correct= "correct";
        public static final String Taken= "taken";



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Question + " TEXT," +
                    A + " TEXT,"+
                    B + " TEXT,"+
                    C + " TEXT,"+
                    D + " TEXT,"+
                    Correct + " TEXT,"+
                    Taken + " TEXT,UNIQUE(_ID)) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
