package com.github.animusanima.a4sewer.model;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;

public class ClothEditingModel
{
    private final int EDIT_STOFF_CURSOR_LOADER_ID = 1;

    private Uri existingClothToEdit;

    private String selectedColor = null;
    private boolean dataWasChanged = false;

    /**
     * 0 = unbekannter Stoff,
     * 1 = Baumwolle,
     * 2 = Jersey,
     * 3 = Leder,
     * 4 = Jeans,
     * 5 = Sweat,
     * 6 = Interlock
     */
    private int category = StoffeTableInformation.KATEGORIE_UNBEKANNT;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            dataWasChanged = true;
            return false;
        }
    };

    public ClothEditingModel(Intent callingIntent)
    {
        existingClothToEdit = callingIntent.getData();
    }

    public boolean isExistingCloth()
    {
        return existingClothToEdit != null;
    }

    public Uri getExistingClothData()
    {
        return existingClothToEdit;
    }

    public int getCategory() {
        return category;
    }

    public void updateCategory(int newCategory)
    {
        this.category = newCategory;
    }

    public boolean isDataWasChanged() {
        return dataWasChanged;
    }

    public View.OnTouchListener getTouchListener() {
        return touchListener;
    }

    public int getCursorLoaderID() {
        return EDIT_STOFF_CURSOR_LOADER_ID;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void updateColor(String color)
    {
        this.selectedColor = color;
    }

    public boolean isUnknownCategory()
    {
        return this.category == StoffeTableInformation.KATEGORIE_UNBEKANNT;
    }
}