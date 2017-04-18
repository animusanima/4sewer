package animusanima.learning.android.stoffkiste.data;

import android.database.Cursor;

import animusanima.learning.android.stoffkiste.db.stoffe.StoffeContract;

public class Stoff
{
    private String name, hersteller, farbe;
    private int laenge, breite, kategorie;
    private double einkaufspreis;

    public Stoff(String name, String hersteller,
                 int laenge, int breite, int kategorie, String farbe,
                 double einkaufspreis)
    {
        this.name = name;
        this.hersteller = hersteller;

        this.laenge = laenge;
        this.breite = breite;
        this.kategorie = kategorie;
        this.farbe = farbe;

        this.einkaufspreis = einkaufspreis;
    }

    public Stoff(Cursor cursor)
    {
        int nameColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_NAME);
        int herstellerColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_HERSTELLER);
        int laengeColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_LAENGE);
        int breiteColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_BREITE);
        int kategorieColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_KATEGORIE);
        int einkaufspreisColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_EINKAUFSPREIS);
        int farbeColumnIndex = cursor.getColumnIndex(StoffeContract.COLUMN_STOFFE_FARBE);

        this.name = cursor.getString(nameColumnIndex);
        this.hersteller = cursor.getString(herstellerColumnIndex);
        this.laenge = cursor.getInt(laengeColumnIndex);
        this.breite = cursor.getInt(breiteColumnIndex);
        this.kategorie = cursor.getInt(kategorieColumnIndex);
        this.einkaufspreis = cursor.getDouble(einkaufspreisColumnIndex);
        this.farbe = cursor.getString(farbeColumnIndex);
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

    public int getKategorie()
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
}