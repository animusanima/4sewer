package com.github.animusanima.a4sewer.Stoffe;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.animusanima.a4sewer.R;
import com.github.animusanima.a4sewer.db.stoffe.StoffCursorAdapter;
import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;

public class StoffActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener
{
    private final int STOFF_CURSOR_ID = 0;

    private StoffCursorAdapter stoffAdapter;

    private Cursor originalData;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoff);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent stoffIntent = new Intent(StoffActivity.this, ScrollingClothEditActivity.class);
                startActivity(stoffIntent);
            }
        });

        stoffAdapter = new StoffCursorAdapter(this, null);

        ListView stoffView = (ListView) findViewById(R.id.stoff_liste);
        stoffView.setAdapter(stoffAdapter);
        stoffView.setOnItemClickListener(this);

        getLoaderManager().initLoader(STOFF_CURSOR_ID, null, this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID)
    {
        Intent stoffEditIntent = new Intent(StoffActivity.this, ScrollingClothEditActivity.class);
        Uri stoffUri = ContentUris.withAppendedId(StoffeTableInformation.CONTENT_URI, ID);
        stoffEditIntent.setData(stoffUri);
        startActivity(stoffEditIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_stoff_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_stoff).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text)
            {
                if ( text.length() == 0 )
                {
                    stoffAdapter.swapCursor(originalData);
                }
                else
                {
                    StringBuilder builder = new StringBuilder();
                    StringBuilder termBuilder = new StringBuilder();
                    termBuilder = termBuilder.append("%")
                                             .append(text.toString())
                                             .append("%");
                    for ( String s : StoffeTableInformation.FILTER_COLUMNS )
                    {
                        if ( builder.length() == 0 )
                        {
                            builder.append(String.format("(%s like ?)", s));
                        }
                        else
                        {
                            builder.append(String.format(" OR (%s like ?)", s));
                        }
                    }
                    Cursor filteredData = getContentResolver().query(StoffeTableInformation.CONTENT_URI,
                            StoffeTableInformation.ALL_COLUMNS,
                            builder.toString(),
                            new String[] { termBuilder.toString(), termBuilder.toString(), termBuilder.toString() },
                            null);
                    stoffAdapter.swapCursor(filteredData);
                    return true;
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(this,
                StoffeTableInformation.CONTENT_URI,
                StoffeTableInformation.ALL_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        originalData = data;
        stoffAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        stoffAdapter.swapCursor(null);
    }
}