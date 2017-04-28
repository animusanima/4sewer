package com.github.animusanima.a4sewer.Stoffe;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.data.CategoryHelper;
import com.github.animusanima.a4sewer.data.Stoff;
import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;
import com.thebluealliance.spectrum.SpectrumPalette;

public class ScrollingClothEditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private final String LOG_TAG = ScrollingClothEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText herstellerEditText;
    private EditText laengeEditText;
    private EditText breiteEditText;
    private EditText einkaufspreisEditText;
    private EditText anzahlEditText;
    private Spinner stoffartSpinner;
    private SpectrumPalette farbauswahl;

    private final int EDIT_STOFF_CURSOR_LOADER_ID = 1;

    private Uri stoffContentUri;

    private String ausgewaehlte_farbe = null;

    private Intent callingIntent;

    FloatingActionButton saveButton, deleteButton;

    /**
     * 0 = unbekannter Stoff,
     * 1 = Baumwolle,
     * 2 = Jersey,
     * 3 = Leder,
     * 4 = Jeans,
     * 5 = Sweat,
     * 6 = Interlock
     */
    private int stoffArt = StoffeTableInformation.KATEGORIE_UNBEKANNT;

    private boolean stoffWasChanged = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            stoffWasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callingIntent = getIntent();
        stoffContentUri = callingIntent.getData();

        saveButton = (FloatingActionButton) findViewById(R.id.action_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoffSpeichern();
                finish();
            }
        });

        deleteButton = (FloatingActionButton) findViewById(R.id.action_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });

        if (stoffContentUri == null)
        {
            setTitle(getString(R.string.stoff_neu));
        } else {
            setTitle(getString(R.string.stoff_bearbeiten));
            deleteButton.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(EDIT_STOFF_CURSOR_LOADER_ID, null, this);
        }

        nameEditText = (EditText) findViewById(R.id.edit_name);
        herstellerEditText = (EditText) findViewById(R.id.edit_hersteller);
        laengeEditText = (EditText) findViewById(R.id.edit_laenge);
        breiteEditText = (EditText) findViewById(R.id.edit_breite);
        einkaufspreisEditText = (EditText) findViewById(R.id.edit_einkaufspreis);
        anzahlEditText = (EditText) findViewById(R.id.edit_anzahl);

        stoffartSpinner = (Spinner) findViewById(R.id.spinner_stoffart);
        farbauswahl = (SpectrumPalette) findViewById(R.id.farb_auswahl);

        farbauswahl.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener()
        {
            @Override
            public void onColorSelected(@ColorInt int color)
            {
                ausgewaehlte_farbe = "#" + Integer.toHexString(color);
            }
        });

        nameEditText.setOnTouchListener(touchListener);
        herstellerEditText.setOnTouchListener(touchListener);
        laengeEditText.setOnTouchListener(touchListener);
        breiteEditText.setOnTouchListener(touchListener);
        einkaufspreisEditText.setOnTouchListener(touchListener);
        stoffartSpinner.setOnTouchListener(touchListener);
        farbauswahl.setOnTouchListener(touchListener);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        final ArrayAdapter stoffartenAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_stoffarten, android.R.layout.simple_spinner_item);

        stoffartenAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        stoffartSpinner.setAdapter(stoffartenAdapter);

        stoffartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    stoffArt = CategoryHelper.getIDByCategory(selection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stoffArt = StoffeTableInformation.KATEGORIE_UNBEKANNT;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!stoffWasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ungesichterte_aenderungen_dialog_nachricht);
        builder.setPositiveButton(R.string.verwerfen, discardButtonClickListener);
        builder.setNegativeButton(R.string.weiterbearbeiten, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    private void stoffSpeichern()
    {
        String nameString = nameEditText.getText().toString().trim();
        String herstellerString = herstellerEditText.getText().toString().trim();
        String laengeString = laengeEditText.getText().toString().trim();
        String breiteString = breiteEditText.getText().toString().trim();
        String einkaufspreisString = einkaufspreisEditText.getText().toString().trim();
        String anzahlString = anzahlEditText.getText().toString().trim();

        if ( noChangesWhereMade(nameString, herstellerString, laengeString,
                breiteString, einkaufspreisString, anzahlString) )
        {
            return;
        }

        ContentValues stoffdata = new ContentValues();
        stoffdata.put(StoffeTableInformation.COLUMN_STOFFE_NAME, nameString);
        stoffdata.put(StoffeTableInformation.COLUMN_STOFFE_HERSTELLER, herstellerString);
        stoffdata.put(StoffeTableInformation.COLUMN_STOFFE_KATEGORIE, CategoryHelper.getCategoryByID(stoffArt));
        stoffdata.put(StoffeTableInformation.COLUMN_STOFFE_FARBE, ausgewaehlte_farbe);
        setDoubleValue(stoffdata, einkaufspreisString, StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
        setIntegerValue(stoffdata, laengeString, StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        setIntegerValue(stoffdata, breiteString, StoffeTableInformation.COLUMN_STOFFE_BREITE);
        setIntegerValue(stoffdata, anzahlString, StoffeTableInformation.COLUMN_STOFFE_ANZAHL);

        if (stoffContentUri == null)
        {
            neuenStoffSpeichern(stoffdata);
        }
        else
        {
            stoffAktualisieren(stoffdata, stoffContentUri);
        }
    }

    private boolean noChangesWhereMade(String nameString, String herstellerString,
                                       String laengeString, String breiteString,
                                       String einkaufspreisString, String anzahlString)
    {
        return (stoffContentUri == null
                && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(herstellerString)
                && TextUtils.isEmpty(laengeString)
                && TextUtils.isEmpty(breiteString)
                && TextUtils.isEmpty(einkaufspreisString)
                && TextUtils.isEmpty(anzahlString)
                && ausgewaehlte_farbe == null
                && stoffArt == StoffeTableInformation.KATEGORIE_UNBEKANNT);
    }

    private void setIntegerValue(ContentValues stoffData, String textValue, String columnName)
    {
        int value = 0;
        if ( !TextUtils.isEmpty(textValue) )
        {
            value = Integer.parseInt(textValue);
        }
        stoffData.put(columnName, value);
    }

    private void setDoubleValue(ContentValues stoffData, String textValue, String columnName)
    {
        double value = 0.0;
        if ( !TextUtils.isEmpty(textValue) )
        {
            value = Double.parseDouble(textValue);
        }
        stoffData.put(columnName, value);
    }

    private void neuenStoffSpeichern(ContentValues stoffData)
    {
        try
        {
            getContentResolver().insert(StoffeTableInformation.CONTENT_URI, stoffData);
        } catch (IllegalArgumentException argEx)
        {
            Log.e(LOG_TAG, "Fehler beim Speichern eines neuen Stoffes", argEx);
        }

    }

    private void stoffAktualisieren(ContentValues petData, Uri petContentUri)
    {
        try
        {
            getContentResolver().update(petContentUri, petData, null, null);
        } catch (Exception ex)
        {
            Log.e(LOG_TAG, "Error during update", ex);
        }
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.loeschen_dialog_nachricht);
        builder.setPositiveButton(R.string.loeschen, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                loescheStoff();
            }
        });
        builder.setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void loescheStoff()
    {
        // Do nothing if not in edit mode
        if (stoffContentUri != null)
        {
            getContentResolver().delete(stoffContentUri, null, null);
        }
        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        Log.d(LOG_TAG, "Inside onCreateLoader");
        return new CursorLoader(this,
                (stoffContentUri != null ? stoffContentUri: StoffeTableInformation.CONTENT_URI),
                StoffeTableInformation.ALL_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        Log.d(LOG_TAG, "Inside onLoadFinished");
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        Log.d(LOG_TAG, "Inside onLoadFinished: Before moveToFirst");
        if (cursor.moveToFirst())
        {
            Log.d(LOG_TAG, "Inside onLoadFinished: Inside moveToFirst");

            Stoff stoff = new Stoff(cursor);

            nameEditText.setText(stoff.getName());
            herstellerEditText.setText(stoff.getHersteller());
            laengeEditText.setText(String.valueOf(stoff.getLaenge()));
            breiteEditText.setText(String.valueOf(stoff.getBreite()));
            einkaufspreisEditText.setText(String.valueOf(stoff.getEinkaufspreis()));
            anzahlEditText.setText(String.valueOf(stoff.getAnzahl()));

            ausgewaehlte_farbe = stoff.getFarbe();
            farbauswahl.setSelectedColor(Color.parseColor(ausgewaehlte_farbe));

            int kategorie = CategoryHelper.getIDByCategory(stoff.getKategorie());
            switch (kategorie) {
                case StoffeTableInformation.KATEGORIE_BAUMWOLLE:
                    stoffartSpinner.setSelection(1);
                    break;
                case StoffeTableInformation.KATEGORIE_JERSEY:
                    stoffartSpinner.setSelection(2);
                    break;
                case StoffeTableInformation.KATEGORIE_LEDER:
                    stoffartSpinner.setSelection(3);
                    break;
                case StoffeTableInformation.KATEGORIE_JEANS:
                    stoffartSpinner.setSelection(4);
                    break;
                case StoffeTableInformation.KATEGORIE_SWEAT:
                    stoffartSpinner.setSelection(5);
                    break;
                case StoffeTableInformation.KATEGORIE_INTERLOCK:
                    stoffartSpinner.setSelection(6);
                    break;
                default:
                    stoffartSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        nameEditText.setText("");
        herstellerEditText.setText("");
        laengeEditText.setText("");
        breiteEditText.setText("");
        einkaufspreisEditText.setText("");
        anzahlEditText.setText("");
        stoffartSpinner.setSelection(0);
        ausgewaehlte_farbe = null;
    }
}