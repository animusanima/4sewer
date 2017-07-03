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
                        "%s TEXT NOT NULL, " + // KATEGORIE
                        "%s TEXT, " + // FARBE
                        "%s INTEGER, " + // LAENGE
                        "%s INTEGER, " + // BREITE
                        "%s REAL," + // EINKAUFSPREIS
                        "%s INTEGER NOT NULL," + // ANZAHL
                        "%s INTEGER," + // PANEL
                        "%s INTEGER," + // MUSTER
                        "%s TEXT);", // FOTO
                StoffeTableInformation.TABLE_NAME, StoffeTableInformation._ID,
                StoffeTableInformation.COLUMN_STOFFE_NAME, StoffeTableInformation.COLUMN_STOFFE_HERSTELLER,
                StoffeTableInformation.COLUMN_STOFFE_KATEGORIE, StoffeTableInformation.COLUMN_STOFFE_FARBE,
                StoffeTableInformation.COLUMN_STOFFE_LAENGE, StoffeTableInformation.COLUMN_STOFFE_BREITE,
                StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS, StoffeTableInformation.COLUMN_STOFFE_ANZAHL,
                StoffeTableInformation.COLUMN_STOFFE_PANEL, StoffeTableInformation.COLUMN_STOFFE_MUSTER,
                StoffeTableInformation.COLUMN_STOFFE_FOTO);

        sqLiteDatabase.execSQL(CREATE_TABLE_STOFFE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
    }
}