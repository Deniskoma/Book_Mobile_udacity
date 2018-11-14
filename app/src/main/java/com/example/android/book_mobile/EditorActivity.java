/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.book_mobile;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.book_mobile.data.BookContract;
import com.example.android.book_mobile.data.BookContract.BookEntry;
import com.example.android.book_mobile.data.BookDbHelper;

/**
 * Allows user to create a new book entry or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the book name */
    private EditText mNameEditText;

    /** EditText field to enter the supplier */
    private EditText mSupplierEditText;

    /** EditText field to enter the quantity */
    private EditText mQuantityEditText;

    /** EditText field to enter the supplier phone */
    private EditText mPhoneEditText;

    /** EditText field to enter the price */
    private EditText mPriceEditText;

    /** EditText field to enter the type */
    private Spinner mBookTypeSpinner;


    /**
     * Type of book. The possible values are:
     * 0 for paper back, 1 for hard cover, 2 audio.
     */
    private int mBookType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_book_name);
        mSupplierEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mQuantityEditText = (EditText) findViewById(R.id.edit_book_quantity);
        mPhoneEditText =(EditText) findViewById(R.id.edit_supplier_phone);
        mPriceEditText =   (EditText) findViewById(R.id.edit_book_price);
        mBookTypeSpinner = (Spinner) findViewById(R.id.spinner_book_type);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter bookTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_book_type_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        bookTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mBookTypeSpinner.setAdapter(bookTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mBookTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_hardcover))) {
                        mBookType = BookEntry.BOOK_TYPE_HARDCOVER; // Hardcover
                    } else if (selection.equals(getString(R.string.type_paperback))) {
                        mBookType = BookEntry.BOOK_TYPE_PAPERBACK; // Paperback
                    } else {
                        mBookType = BookEntry.BOOK_TYPE_AUDIO; // Audio
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBookType = 0; // Audio
            }
        });
    }
    // Get input from user and insert into database.
    private void insertBook(){
        String nameString = mNameEditText.getText().toString().trim();
        String supplierString = mSupplierEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String phoneString = mPhoneEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        //Integer.parseInt("1")->1
        int quantity = Integer.parseInt(quantityString);

        BookDbHelper mDbHelper = new BookDbHelper(this);
        //Gets the data repository in write mode.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BookEntry.COLUMN_BOOK_NAME,nameString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, priceString);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME,supplierString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE,phoneString);
        values.put(BookEntry.COLUMN_BOOK_TYPE, mBookType);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY,quantity);

        long newRowId = db.insert (BookContract.BookEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID" + newRowId);

        if (newRowId == -1){
            Toast.makeText(this,"Error with saving book", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Book saved with row id: "+newRowId, Toast.LENGTH_SHORT).show();
    }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save Pet to database.
                insertBook();
                //Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}