package com.github.animusanima.a4sewer.Stoffe;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.data.CategoryHelper;
import com.github.animusanima.a4sewer.data.Stoff;
import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;
import com.github.animusanima.a4sewer.model.ClothEditingModel;

public class ScrollingClothEditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private final String LOG_TAG = ScrollingClothEditActivity.class.getSimpleName();

    private ClothEditingModel model;

    private FloatingActionButton saveButton, deleteButton;

    private EditText nameEditText;
    private EditText herstellerEditText;
    private EditText laengeEditText;
    private EditText breiteEditText;
    private EditText einkaufspreisEditText;
    private EditText anzahlEditText;
    private EditText farbeEditText;
    private Spinner stoffartSpinner;
    private CheckBox panelCheckBox;
    private CheckBox musterCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent callingIntent = getIntent();
        model = new ClothEditingModel(callingIntent);

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

        if (!model.isExistingCloth())
        {
            setTitle(getString(R.string.stoff_neu));
        } else {
            setTitle(getString(R.string.stoff_bearbeiten));
            deleteButton.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(model.getCursorLoaderID(), null, this);
        }

        nameEditText = (EditText) findViewById(R.id.edit_name);
        herstellerEditText = (EditText) findViewById(R.id.edit_hersteller);
        laengeEditText = (EditText) findViewById(R.id.edit_laenge);
        breiteEditText = (EditText) findViewById(R.id.edit_breite);
        einkaufspreisEditText = (EditText) findViewById(R.id.edit_einkaufspreis);
        anzahlEditText = (EditText) findViewById(R.id.edit_anzahl);
        farbeEditText = (EditText) findViewById(R.id.edit_farbe);
        stoffartSpinner = (Spinner) findViewById(R.id.spinner_stoffart);
        panelCheckBox = (CheckBox) findViewById(R.id.panel_checkbox);
        musterCheckBox = (CheckBox) findViewById(R.id.muster_checkbox);

        nameEditText.setOnTouchListener(model.getTouchListener());
        herstellerEditText.setOnTouchListener(model.getTouchListener());
        laengeEditText.setOnTouchListener(model.getTouchListener());
        breiteEditText.setOnTouchListener(model.getTouchListener());
        einkaufspreisEditText.setOnTouchListener(model.getTouchListener());
        stoffartSpinner.setOnTouchListener(model.getTouchListener());
        farbeEditText.setOnTouchListener(model.getTouchListener());

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
                    model.updateCategory(CategoryHelper.getIDByCategory(selection));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                model.updateCategory(StoffeTableInformation.KATEGORIE_UNBEKANNT);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!model.isDataWasChanged()) {
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
        String farbeString = farbeEditText.getText().toString().trim();
        Integer panelValue = panelCheckBox.isChecked() ? 1 : 0;
        Integer musterValue = musterCheckBox.isChecked() ? 1 : 0;

        if ( noChangesWhereMade(nameString, herstellerString, laengeString,
                breiteString, einkaufspreisString, anzahlString) )
        {
            return;
        }

        ContentValues stoffData = new ContentValues();
        stoffData.put(StoffeTableInformation.COLUMN_STOFFE_NAME, nameString);
        stoffData.put(StoffeTableInformation.COLUMN_STOFFE_HERSTELLER, herstellerString);
        stoffData.put(StoffeTableInformation.COLUMN_STOFFE_KATEGORIE, CategoryHelper.getCategoryByID(model.getCategory()));
        stoffData.put(StoffeTableInformation.COLUMN_STOFFE_FARBE, farbeString);
        setDoubleValue(stoffData, einkaufspreisString, StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
        setIntegerValue(stoffData, laengeString, StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        setIntegerValue(stoffData, breiteString, StoffeTableInformation.COLUMN_STOFFE_BREITE);
        setIntegerValue(stoffData, anzahlString, StoffeTableInformation.COLUMN_STOFFE_ANZAHL);

        //stoffData.put(StoffeTableInformation.COLUMN_STOFFE_PANEL, panelValue);
        //stoffData.put(StoffeTableInformation.COLUMN_STOFFE_MUSTER, musterValue);

        setIntegerValue(stoffData, String.valueOf(panelValue), StoffeTableInformation.COLUMN_STOFFE_PANEL);
        setIntegerValue(stoffData, String.valueOf(musterValue), StoffeTableInformation.COLUMN_STOFFE_MUSTER);

        if (!model.isExistingCloth())
        {
            neuenStoffSpeichern(stoffData);
        }
        else
        {
            stoffAktualisieren(stoffData, model.getExistingClothData());
        }
    }

    private boolean noChangesWhereMade(String nameString, String herstellerString,
                                       String laengeString, String breiteString,
                                       String einkaufspreisString, String anzahlString)
    {
        return (!model.isExistingCloth()
                && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(herstellerString)
                && TextUtils.isEmpty(laengeString)
                && TextUtils.isEmpty(breiteString)
                && TextUtils.isEmpty(einkaufspreisString)
                && TextUtils.isEmpty(anzahlString)
                && model.getSelectedColor() == null
                && model.isUnknownCategory());
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
        if (model.isExistingCloth())
        {
            getContentResolver().delete(model.getExistingClothData(), null, null);
        }
        finish();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        Log.d(LOG_TAG, "Inside onCreateLoader");
        return new CursorLoader(this,
                (model.isExistingCloth() ? model.getExistingClothData() : StoffeTableInformation.CONTENT_URI),
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
            farbeEditText.setText(stoff.getFarbe());
            panelCheckBox.setChecked(stoff.isPanel());
            musterCheckBox.setChecked(stoff.isMuster());

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
        model.updateColor(null);
        farbeEditText.setText("");
        panelCheckBox.setChecked(false);
        musterCheckBox.setChecked(false);
    }
}