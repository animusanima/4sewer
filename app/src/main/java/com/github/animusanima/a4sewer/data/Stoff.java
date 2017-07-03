package com.github.animusanima.a4sewer.data;

import android.database.Cursor;

import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;

public class Stoff
{
    private String name;
    private String manufacturer;
    private String color;
    private String category;
    private String imagePath;

    private int ID, length, width, amount;
    private double buyersPrice;
    private boolean isPanel, isMuster;

    public Stoff(Cursor cursor)
    {
        int idColumnIndex = cursor.getColumnIndex(StoffeTableInformation._ID);
        int nameColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_NAME);
        int herstellerColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_HERSTELLER);
        int laengeColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        int breiteColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_BREITE);
        int kategorieColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_KATEGORIE);
        int einkaufspreisColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
        int farbeColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_FARBE);
        int anzahlColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_ANZAHL);
        int panelColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_PANEL);
        int musterColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_MUSTER);
        int fotoIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_FOTO);

        this.ID = cursor.getInt(idColumnIndex);
        this.name = cursor.getString(nameColumnIndex);
        this.manufacturer = cursor.getString(herstellerColumnIndex);
        this.length = cursor.getInt(laengeColumnIndex);
        this.width = cursor.getInt(breiteColumnIndex);
        this.category = cursor.getString(kategorieColumnIndex);
        this.buyersPrice = cursor.getDouble(einkaufspreisColumnIndex);
        this.color = cursor.getString(farbeColumnIndex);
        this.amount = cursor.getInt(anzahlColumnIndex);
        this.isPanel = cursor.getInt(panelColumnIndex) == 1;
        this.isMuster = cursor.getInt(musterColumnIndex) == 1;
        this.imagePath = cursor.getString(fotoIndex);
    }

    public int getID() {
        return ID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public int getLength()
    {
        return length;
    }

    public int getWidth()
    {
        return width;
    }

    public String getCategory()
    {
        return category;
    }

    public double getBuyersPrice()
    {
        return buyersPrice;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isPanel() {
        return this.isPanel;
    }

    public boolean isMuster() {
        return this.isMuster;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setBuyersPrice(double buyersPrice) {
        this.buyersPrice = buyersPrice;
    }

    public void setPanel(boolean panel) {
        isPanel = panel;
    }

    public void setMuster(boolean muster) {
        isMuster = muster;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}