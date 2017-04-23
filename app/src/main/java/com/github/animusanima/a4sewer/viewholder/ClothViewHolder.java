package com.github.animusanima.a4sewer.viewholder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.data.CategoryHelper;
import com.github.animusanima.a4sewer.data.Stoff;

public class ClothViewHolder extends RecyclerView.ViewHolder
{
    TextView nameTextView;
    TextView herstellerTextView;
    TextView kategorieTextView;
    TextView anzahlTextView;

    public ClothViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.titel_view);
        herstellerTextView = (TextView) itemView.findViewById(R.id.hersteller_view);
        kategorieTextView = (TextView) itemView.findViewById(R.id.kategorie_view);
        anzahlTextView = (TextView) itemView.findViewById(R.id.anzahl_view);
    }

    public void bindCursor(Cursor cursor)
    {
        Stoff stoff = new Stoff(cursor);

        nameTextView.setText(stoff.getName());
        herstellerTextView.setText(stoff.getHersteller());

        String kategorie = CategoryHelper.getCategoryByID(stoff.getKategorie());
        kategorieTextView.setText(kategorie);

        anzahlTextView.setText(stoff.getAnzahl());
    }
}