package com.example.rahul.vaccination.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rahul.vaccination.data.ChildContract.ChildEntry.*;

import static com.example.rahul.vaccination.data.ChildContract.ChildEntry;


/**
 * Created by rahul on 27/3/17.
 */

public class VaccineHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Vaccine.db";
    public static final int DATABASE_VERSION = 1;

    public VaccineHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CHILD_TABLE = "CREATE TABLE "+ ChildEntry.TABLE_NAME + "(" +
                ChildEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ChildEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ChildEntry.COLUMN_FATHER + " TEXT NOT NULL, " +
                ChildEntry.COLUMN_MOTHER + " TEXT NOT NULL, " +
                ChildEntry.COLUMN_GENDER + " INTEGER NOT NULL, +" +
                ChildEntry.COLUMN_DOB + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_CHILD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
