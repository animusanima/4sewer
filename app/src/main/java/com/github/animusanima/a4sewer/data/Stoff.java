package com.github.animusanima.a4sewer.data;

import android.database.Cursor;

import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;

public class Stoff
{
    private String name, hersteller, farbe, kategorie;
    private int laenge, breite,  anzahl;
    private double einkaufspreis;

    public Stoff(String name, String hersteller,
                 int laenge, int breite, String kategorie, String farbe,
                 double einkaufspreis, int anzahl)
    {
        this.name = name;
        this.hersteller = hersteller;

        this.laenge = laenge;
        this.breite = breite;
        this.kategorie = kategorie;
        this.farbe = farbe;

        this.einkaufspreis = einkaufspreis;
        this.anzahl = anzahl;
    }

    public Stoff(Cursor cursor)
    {
        int nameColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_NAME);
        int herstellerColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_HERSTELLER);
        int laengeColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_LAENGE);
        int breiteColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_BREITE);
        int kategorieColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_KATEGORIE);
        int einkaufspreisColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_EINKAUFSPREIS);
        int farbeColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_FARBE);
        int anzahlColumnIndex = cursor.getColumnIndex(StoffeTableInformation.COLUMN_STOFFE_ANZAHL);

        this.name = cursor.getString(nameColumnIndex);
        this.hersteller = cursor.getString(herstellerColumnIndex);
        this.laenge = cursor.getInt(laengeColumnIndex);
        this.breite = cursor.getInt(breiteColumnIndex);
        this.kategorie = cursor.getString(kategorieColumnIndex);
        this.einkaufspreis = cursor.getDouble(einkaufspreisColumnIndex);
        this.farbe = cursor.getString(farbeColumnIndex);
        this.anzahl = cursor.getInt(anzahlColumnIndex);
    }

    public String getHersteller()
    {
        return hersteller;
    }

    public int getLaenge()
    {
        return laenge;
    }

    public int getBreite()
    {
        return breite;
    }

    public String getKategorie()
    {
        return kategorie;
    }

    public double getEinkaufspreis()
    {
        return einkaufspreis;
    }

    public String getName()
    {
        return name;
    }

    public String getFarbe()
    {
        return farbe;
    }

    public int getAnzahl() {
        return anzahl;
    }
}