package com.example.tentsering.googlebookreloaded;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Ebooks>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;

    /**
     * New {@link BookLoader}
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url){
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading() method is called");
        forceLoad();
    }

    @Override
    public List<Ebooks> loadInBackground(){
        Log.i(LOG_TAG, "loadInBackground() method is called");
        if(mUrl == null){
            return null;
        }
        List<Ebooks> ebooks = BookUtils.extractBookData(mUrl);
        return ebooks;
    }
}































