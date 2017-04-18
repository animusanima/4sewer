package animusanima.learning.android.stoffkiste.db.stoffe;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thebluealliance.spectrum.SpectrumPalette;

import animusanima.learning.android.stoffkiste.R;
import animusanima.learning.android.stoffkiste.data.CategoryHelper;
import animusanima.learning.android.stoffkiste.data.Stoff;

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
        return LayoutInflater.from(context).inflate(R.layout.stoff_item_layout, parent , false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView nameTextView = (TextView) view.findViewById(R.id.titel_view);
        TextView herstellerTextView = (TextView) view.findViewById(R.id.hersteller_view);
        TextView kategorieTextView = (TextView) view.findViewById(R.id.kategorie_view);
        LinearLayout stoffEintrag = (LinearLayout) view.findViewById(R.id.stoff_item);

        this.stoff = new Stoff(cursor);

        String farbe = stoff.getFarbe();
        stoffEintrag.setBackgroundColor(Color.parseColor(farbe));

        nameTextView.setText(stoff.getName());
        herstellerTextView.setText(stoff.getHersteller());
        kategorieTextView.setText(CategoryHelper.getCategoryByID(stoff.getKategorie()));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
        return super.runQueryOnBackgroundThread(constraint);
    }
}