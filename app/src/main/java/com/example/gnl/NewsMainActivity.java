package com.example.gnl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class NewsMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<newslist>> {

    private static final int LOADER_ID_News = 1;
    private static final String URL_NEWS = "https://content.guardianapis.com/search";
    private TextView mEmptyStateTextView;
    private Myadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_news);

        ListView NewsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.emptyView);
        NewsListView.setEmptyView(mEmptyStateTextView);

        //new adapter for newslist
        mAdapter = new Myadapter(this, new ArrayList<newslist>());

        NewsListView.setAdapter(mAdapter);

        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // current news click
                newslist currentNews = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.geturlfornews());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        // network connectivity
        ConnectivityManager s = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // currently active network
        NetworkInfo networkInfo = s.getActiveNetworkInfo();

        // fetch data if connection
        if (networkInfo != null && networkInfo.isConnected()) {
            // LoaderManager to interact with loaders
            LoaderManager loaderManager = getSupportLoaderManager();

            // loader initialize
            loaderManager.initLoader(LOADER_ID_News, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading to show error
            View loadingIndicator = findViewById(R.id.loading);
            loadingIndicator.setVisibility(View.GONE);
            //  no connection message
            mEmptyStateTextView.setText(R.string.not_connect);
        }
    }


    @NonNull
    @Override
    public Loader<List<newslist>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(URL_NEWS);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api-key", "test");

        return new Loadernews(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<newslist>> loader, List<newslist> n) {
        // Hide loading it is loaded
        View loadingIndicator = findViewById(R.id.loading);
        loadingIndicator.setVisibility(View.GONE);
        // Clear the adapter
        mAdapter.clear();
        mEmptyStateTextView.setText(R.string.nodata);

        if (n != null && !n.isEmpty()) {
            mAdapter.addAll(n);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<newslist>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
