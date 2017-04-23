package com.github.animusanima.a4sewer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.db.stoffe.StoffCursorAdapter;
import com.github.animusanima.a4sewer.viewholder.ClothViewHolder;

/**
 * Created by root on 23.04.17.
 */

public class ClothRecyclerAdapter extends RecyclerView.Adapter<ClothViewHolder>
{
    private ClothViewHolder viewHolder;
    private StoffCursorAdapter adapter;
    private Context context;

    public ClothRecyclerAdapter(Context context, Cursor c)
    {
        this.context = context;
        this.adapter = new StoffCursorAdapter(context, c);
    }

    @Override
    public ClothViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Cursor c = adapter.getCursor();
        View itemView = adapter.newView(context, c, parent);
        viewHolder = new ClothViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClothViewHolder holder, int position)
    {
        Cursor c = adapter.getCursor();
        c.moveToPosition(position);
        adapter.bindView(viewHolder.itemView, context, c);
    }

    @Override
    public int getItemCount()
    {
        return adapter.getCount();
    }
}