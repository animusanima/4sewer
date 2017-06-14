package com.github.animusanima.a4sewer.db.stoffe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.data.Stoff;

public class StoffCursorAdapter extends CursorAdapter
{
    private Stoff stoff;

    public StoffCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        // stoff_item_layout
        return LayoutInflater.from(context).inflate(R.layout.stoff_item_layout, parent , false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView nameTextView = (TextView) view.findViewById(R.id.titel_view);
        TextView herstellerTextView = (TextView) view.findViewById(R.id.hersteller_view);
        TextView kategorieTextView = (TextView) view.findViewById(R.id.kategorie_view);
        TextView anzahlTextView = (TextView) view.findViewById(R.id.anzahl_view);
        CheckBox panelView = (CheckBox) view.findViewById(R.id.list_panel_view);
        CheckBox musterView = (CheckBox) view.findViewById(R.id.list_muster_view);

        this.stoff = new Stoff(cursor);

        nameTextView.setText(stoff.getName());
        herstellerTextView.setText(stoff.getHersteller());
        kategorieTextView.setText(stoff.getKategorie());
        //anzahlTextView.setText(String.valueOf(stoff.getAnzahl() + "x"));

        //panelView.setChecked(stoff.isPanel());
        //panelView.setEnabled(false);
        //musterView.setChecked(stoff.isMuster());
        //musterView.setEnabled(false);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
        return super.runQueryOnBackgroundThread(constraint);
    }
}