package com.example.android.book_mobile.data;

import android.provider.BaseColumns;

/**
 * Created by denis on 9/7/2018.
 */

public final class BookContract {
 public static abstract class BookEntry implements BaseColumns {

    public static final String TABLE_NAME = "books";
    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_BOOK_NAME = "title";
    public static final String COLUMN_BOOK_PRICE = "price";
    public static final String COLUMN_SUPPLIER_NAME = "supplier";
    public static final String COLUMN_SUPPLIER_PHONE = "phone";
    public static final String COLUMN_BOOK_TYPE = "type";
    public static final String COLUMN_BOOK_QUANTITY = "quantity";
    /**
     * Possible values for the types.
     */
    public static final int BOOK_TYPE_AUDIO = 0;
    public static final int BOOK_TYPE_HARDCOVER = 1;
    public static final int BOOK_TYPE_PAPERBACK = 2;
   }
}

