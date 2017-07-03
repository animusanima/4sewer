package com.github.animusanima.a4sewer.db.stoffe;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.data.Stoff;

import java.io.File;
import java.io.FileInputStream;

public class StoffCursorAdapter extends CursorAdapter
{
    private Stoff stoff;
    private ContentResolver contentResolver;
    private Context context;

    public StoffCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
        this.contentResolver = context.getContentResolver();
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        // stoff_item_layout
        return LayoutInflater.from(context).inflate(R.layout.stoff_item_layout, parent , false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor)
    {
        TextView nameTextView = (TextView) view.findViewById(R.id.titel_view);
        TextView herstellerTextView = (TextView) view.findViewById(R.id.hersteller_view);
        TextView kategorieTextView = (TextView) view.findViewById(R.id.kategorie_view);
        TextView anzahlTextView = (TextView) view.findViewById(R.id.anzahl_view);
        ImageView panelView = (ImageView) view.findViewById(R.id.list_panel_image);
        ImageView musterView = (ImageView) view.findViewById(R.id.list_muster_image);
        ImageView stoffImage = (ImageView) view.findViewById(R.id.stoff_image);

        this.stoff = new Stoff(cursor);

        nameTextView.setText(stoff.getName());
        herstellerTextView.setText(stoff.getManufacturer());
        kategorieTextView.setText(stoff.getCategory());
        anzahlTextView.setText(String.valueOf(stoff.getAmount() + "x"));

        panelView.setVisibility(stoff.isPanel() ? View.VISIBLE : View.GONE);
        musterView.setVisibility(stoff.isMuster() ? View.VISIBLE : View.GONE);

        File f = new File(stoff.getImagePath());
        if (f.exists())
        {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = true;
            opts.inJustDecodeBounds = false;

            try {
                FileInputStream input = new FileInputStream(f);
                Bitmap b = BitmapFactory.decodeStream(input);
                stoffImage.setImageBitmap(b);
                stoffImage.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }

        ImageView deleteButton = (ImageView) view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmation(stoff.getID());
            }
        });
    }

    private void showDeleteConfirmation(final long item_ID)
    {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.loeschen_dialog_nachricht);
        builder.setPositiveButton(R.string.loeschen, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Uri stoffUri = ContentUris.withAppendedId(StoffeTableInformation.CONTENT_URI, item_ID);
                contentResolver.delete(stoffUri, null, null);
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

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
        return super.runQueryOnBackgroundThread(constraint);
    }


}