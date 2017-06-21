package com.github.animusanima.a4sewer.db.stoffe;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class StoffeTableInformation implements BaseColumns
{
    public static final String CONTENT_AUTHORITY = "com.github.animusanima.a4sewer";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_STOFFE = "stoffe";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STOFFE);

    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOFFE;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOFFE;

    private StoffeTableInformation() {}

    public final static String TABLE_NAME = "stoffe";

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_STOFFE_NAME = "name";
    public final static String COLUMN_STOFFE_HERSTELLER = "hersteller";
    public final static String COLUMN_STOFFE_LAENGE = "laenge";
    public final static String COLUMN_STOFFE_BREITE = "breite";
    public final static String COLUMN_STOFFE_KATEGORIE = "kategorie";
    public final static String COLUMN_STOFFE_FARBE = "farbe";
    public final static String COLUMN_STOFFE_EINKAUFSPREIS = "einkaufspreis";
    public final static String COLUMN_STOFFE_ANZAHL = "anzahl";
    public final static String COLUMN_STOFFE_PANEL = "panel";
    public final static String COLUMN_STOFFE_MUSTER = "muster";
    public final static String COLUMN_STOFFE_FOTO = "foto";

    public final static String[] ALL_COLUMNS = new String[] {_ID, COLUMN_STOFFE_NAME, COLUMN_STOFFE_HERSTELLER,
            COLUMN_STOFFE_LAENGE, COLUMN_STOFFE_BREITE, COLUMN_STOFFE_KATEGORIE, COLUMN_STOFFE_FARBE,
            COLUMN_STOFFE_EINKAUFSPREIS, COLUMN_STOFFE_ANZAHL, COLUMN_STOFFE_PANEL,
            COLUMN_STOFFE_MUSTER, COLUMN_STOFFE_FOTO};

    public final static String[] FILTER_COLUMNS = new String[] {COLUMN_STOFFE_NAME, COLUMN_STOFFE_HERSTELLER,
            COLUMN_STOFFE_KATEGORIE};

    public final static int KATEGORIE_UNBEKANNT = 0;
    public final static int KATEGORIE_BAUMWOLLE = 1;
    public final static int KATEGORIE_JERSEY = 2;
    public final static int KATEGORIE_LEDER = 3;
    public final static int KATEGORIE_JEANS = 4;
    public final static int KATEGORIE_SWEAT = 5;
    public final static int KATEGORIE_INTERLOCK = 6;
}