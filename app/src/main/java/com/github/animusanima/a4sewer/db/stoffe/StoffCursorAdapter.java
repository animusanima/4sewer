package com.github.animusanima.a4sewer.db.stoffe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
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
        ImageView panelView = (ImageView) view.findViewById(R.id.list_panel_image);
        ImageView musterView = (ImageView) view.findViewById(R.id.list_muster_image);

        this.stoff = new Stoff(cursor);

        nameTextView.setText(stoff.getName());
        herstellerTextView.setText(stoff.getHersteller());
        kategorieTextView.setText(stoff.getKategorie());
        anzahlTextView.setText(String.valueOf(stoff.getAnzahl() + "x"));

        panelView.setVisibility(stoff.isPanel() ? View.VISIBLE : View.GONE);
        musterView.setVisibility(stoff.isMuster() ? View.VISIBLE : View.GONE);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
        return super.runQueryOnBackgroundThread(constraint);
    }
}