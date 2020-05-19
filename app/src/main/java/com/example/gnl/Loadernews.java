package com.example.gnl;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.List;

public class Loadernews  extends AsyncTaskLoader<List<newslist>> {
    //url to fetch
    private String mUrl;

    public Loadernews(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //background thread
    @Override
    public List<newslist> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<newslist> news = newstaker.fetchnews(mUrl);
        return news;
    }
}
