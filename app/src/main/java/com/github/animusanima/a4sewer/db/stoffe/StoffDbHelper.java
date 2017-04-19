package com.github.animusanima.a4sewer.db.stoffe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by animusanima on 09.04.17.
 */

public class StoffDbHelper extends SQLiteOpenHelper
{
    public static final String LOG_TAG = StoffDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "stoffe.db";
    private static final int DATABASE_VERSION = 1;

    public StoffDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_TABLE_STOFFE = String.format("CREATE TABLE %s" +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID
                        "%s TEXT NOT NULL, " + // NAME
                        "%s TEXT NOT NULL, " + // HERSTELLER
                        "%s INTEGER NOT NULL, " + // KATEGORIE
                        "%s TEXT NOT NULL, " + // FARBE
                        "%s INTEGER NOT NULL, " + // LAENGE
                        "%s INTEGER NOT NULL, " + // BREITE
                        "%s REAL NOT NULL);", // EINKAUFSPREIS
                StoffeContract.TABLE_NAME, StoffeContract._ID,
                StoffeContract.COLUMN_STOFFE_NAME, StoffeContract.COLUMN_STOFFE_HERSTELLER,
                StoffeContract.COLUMN_STOFFE_KATEGORIE, StoffeContract.COLUMN_STOFFE_FARBE,
                StoffeContract.COLUMN_STOFFE_LAENGE, StoffeContract.COLUMN_STOFFE_BREITE,
                StoffeContract.COLUMN_STOFFE_EINKAUFSPREIS);
        sqLiteDatabase.execSQL(CREATE_TABLE_STOFFE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
    }
}