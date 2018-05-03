package com.example.tentsering.googlebookreloaded;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookSearch extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Ebooks>> {

    private static final String LOG_TAG = BookSearch.class.getName();
    public static final String UNIQUE_MESSAGE = "com.example.my_first_app_message";
    private static String actual_webiste = "";
    private static String query_param = "";

    public static EbooksAdapter ebooksAdapter;
    public static ListView bookListView;
    public static TextView showEmpty;

    private static final int EBOOK_LOADER_ID = 1;

    // implements LoaderManager.LoaderCallbacks<List<Ebooks>>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_lists);

        Intent intent = getIntent();
        query_param = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        actual_webiste = "https://www.googleapis.com/books/v1/volumes?q=" + query_param
                +"&maxResults=15&printType=books";

        showEmpty = (TextView) findViewById(R.id.showEmpty);
        bookListView = (ListView) findViewById(R.id.book_Lists);

        ebooksAdapter = new EbooksAdapter(BookSearch.this, new ArrayList<Ebooks>());
        bookListView.setAdapter(ebooksAdapter);

        bookListView.setEmptyView(showEmpty);

        //open browser when clicked on the view
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //find the current ebook view that was clicked
                Ebooks currentbook = ebooksAdapter.getItem(position);
                //Convert the String url to a URI object\
                Uri ebookUri = Uri.parse(currentbook.getmBookLink());

                        Intent webIntent = new Intent(BookSearch.this, LoadMyWebsite.class);
                        webIntent.putExtra(UNIQUE_MESSAGE, currentbook.getmBookLink());
                        startActivity(webIntent);
            }

        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
            Log.i(LOG_TAG, "calling LoaderManager...");
            LoaderManager loaderManager = getLoaderManager();
            //initialize the loader, fill the parameter, for eg. intitLoader(id, null, context)
            //you put, initLoader(EARTHQUAKE_LOADER, null, this),
            loaderManager.initLoader(EBOOK_LOADER_ID, null, this);
        } else {
            bookListView.setVisibility(View.GONE);
            showEmpty.setText("No internet connection, perhaps, airplane mode!");

        }
        //EarthquakeLoader earthquakeLoader = new EarthquakeLoader(EarthquakeActivity.this, USGS_WEBSITE);

    }

    @Override
    public Loader<List<Ebooks>> onCreateLoader(int i, Bundle bundle) {
        //Create new loader for the given URL
        Log.i(LOG_TAG, "onCreateLoader() is called");
        return new BookLoader(BookSearch.this, actual_webiste);
    }

    @Override
    public void onLoadFinished(Loader<List<Ebooks>> loader, List<Ebooks> ebooks) {
        //clear the adapter of previous earthquake data
        Log.i(LOG_TAG, "onLoadFinished()is called");
        ebooksAdapter.clear();
        //If there's a valid list of Earthquake object, this will trigger the ListView to update.
        if (ebooks != null && !ebooks.isEmpty()) {
            ebooksAdapter.addAll(ebooks);


        }
    }
        @Override
        public void onLoaderReset (Loader < List < Ebooks >> loader) {
            //Loader reset, so we can clear out our existing data.
            Log.i(LOG_TAG, "onLoaderReset()is called");
            ebooksAdapter.clear();
        }

}



