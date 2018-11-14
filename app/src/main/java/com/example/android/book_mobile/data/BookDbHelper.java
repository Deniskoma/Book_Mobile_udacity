package com.example.android.book_mobile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.book_mobile.data.BookContract.BookEntry;

/**
 * Created by denis on 9/7/2018.
 */

public class BookDbHelper extends SQLiteOpenHelper  {
    /** Name of the database file*/
    private static final String DATABASE_NAME = "books.db";
            /** Database version. If you change the database schema, you must increment the database version*/
    private static final int DATABASE_VERSION = 2;
    public BookDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, price INTEGER NOT NULL DEFAULT 0, quantity INTEGER NOT NULL DEFAULT 0
              //  , supplier TEXT, phone INTEGER NOT NULL DEFAULT 0, type INTEGER NOT NULL);
        //  create a string that contains a SQL statement to create the books table
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + "("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL,"
                + BookEntry.COLUMN_BOOK_PRICE + " INTEGER NOT NULL DEFAULT 0);"
                + BookEntry.COLUMN_SUPPLIER_NAME + " TEXT,"
                + BookEntry.COLUMN_SUPPLIER_PHONE + " INTEGER NOT NULL DEFAULT 0);"
                + BookEntry.COLUMN_BOOK_TYPE + " INTEGER NOT NULL,"
                + BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
