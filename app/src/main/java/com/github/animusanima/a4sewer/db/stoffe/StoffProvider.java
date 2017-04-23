package com.github.animusanima.a4sewer.db.stoffe;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class StoffProvider extends ContentProvider
{
    /** Tag for the log messages */
    public static final String LOG_TAG = StoffProvider.class.getSimpleName();

    private StoffDbHelper dbHelper;

    /** URI matcher code for the content URI for the pets table */
    private static final int STOFFE = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int STOFFE_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(StoffeTableInformation.CONTENT_AUTHORITY, StoffeTableInformation.PATH_STOFFE, STOFFE);
        sUriMatcher.addURI(StoffeTableInformation.CONTENT_AUTHORITY, StoffeTableInformation.PATH_STOFFE + "/#", STOFFE_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        dbHelper = new StoffDbHelper(getContext());
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder)
    {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch ( match )
        {
            case STOFFE:
                cursor = database.query(StoffeTableInformation.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case STOFFE_ID:
                selection = StoffeTableInformation._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(StoffeTableInformation.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        validateInsertValues(contentValues);

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long newID = db.insert(StoffeTableInformation.TABLE_NAME, null, contentValues);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (newID == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, newID);
    }

    private void validateInsertValues(ContentValues contentValues)
    {
        validateStringParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_NAME);
        validateStringParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_HERSTELLER);
        validateIntegerParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_KATEGORIE);
        validateIntegerParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        validateIntegerParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_BREITE);
        validateStringParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_FARBE);
        validateDoubleParameter(contentValues, StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
    }

    private void validateStringParameter(ContentValues contentValues, String columnName)
    {
        String value = contentValues.getAsString(columnName);
        if ( value == null || value.isEmpty() )
            throw new IllegalArgumentException(String.format("%s muss gefüllt sein", columnName));
    }

    private void validateIntegerParameter(ContentValues contentValues, String columnName)
    {
        Integer parameter = contentValues.getAsInteger(columnName);
        if (parameter != null && parameter < 0)
            throw new IllegalArgumentException(String.format("%s muss gefüllt sein", columnName));
    }

    private void validateDoubleParameter(ContentValues contentValues, String columnName)
    {
        Double parameter = contentValues.getAsDouble(columnName);
        if (parameter != null && parameter < 0.0)
            throw new IllegalArgumentException(String.format("%s muss gefüllt sein", columnName));
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case STOFFE:
                return aktualisiereStoffe(uri, contentValues, selection, selectionArgs);
            case STOFFE_ID:
                selection = StoffeTableInformation._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return aktualisiereStoffe(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int aktualisiereStoffe(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        if (values.size() == 0)
        {
            return 0;
        }
        validateUpdateValues(values);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.update(StoffeTableInformation.TABLE_NAME, values, selection, selectionArgs);

        if (rowsAffected >= 1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    private void validateUpdateValues(ContentValues values)
    {
        validateStringColumn(values, StoffeTableInformation.COLUMN_STOFFE_NAME);
        validateStringColumn(values, StoffeTableInformation.COLUMN_STOFFE_HERSTELLER);
        validateStringColumn(values, StoffeTableInformation.COLUMN_STOFFE_FARBE);
        validateIntegerColumn(values, StoffeTableInformation.COLUMN_STOFFE_KATEGORIE);
        validateIntegerColumn(values, StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        validateIntegerColumn(values, StoffeTableInformation.COLUMN_STOFFE_BREITE);
        validateDoubleColumn(values, StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
    }

    private void validateStringColumn(ContentValues values, String columnName)
    {
        if (values.containsKey(columnName))
        {
            String value = values.getAsString(columnName);
            if (value == null)
                throw new IllegalArgumentException(String.format("Column %s requires a value", columnName));
        }
    }

    private void validateIntegerColumn(ContentValues values, String columnName)
    {
        if (values.containsKey(columnName))
        {
            Integer value = values.getAsInteger(columnName);
            if (value == null || value < 0)
                throw new IllegalArgumentException(String.format("Column %s requires a valid value", columnName));
        }
    }

    private void validateDoubleColumn(ContentValues values, String columnName)
    {
        if (values.containsKey(columnName))
        {
            Double value = values.getAsDouble(columnName);
            if (value == null || value < 0.0)
                throw new IllegalArgumentException(String.format("Column %s requires a valid value", columnName));
        }
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int deletedRows;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STOFFE:
                // Delete all rows that match the selection and selection args
                deletedRows = database.delete(StoffeTableInformation.TABLE_NAME, selection, selectionArgs);
                break;
            case STOFFE_ID:
                // Delete a single row given by the ID in the URI
                selection = StoffeTableInformation._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                deletedRows = database.delete(StoffeTableInformation.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (deletedRows >= 1)
            getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STOFFE:
                return StoffeTableInformation.CONTENT_LIST_TYPE;
            case STOFFE_ID:
                return StoffeTableInformation.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
