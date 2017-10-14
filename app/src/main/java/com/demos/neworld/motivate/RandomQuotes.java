package com.demos.neworld.motivate;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class RandomQuotes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    private TextView mQuotes;
    private static final int LOADER_ID = 1;
    private static final String KEY_NUM_QUOTES = "num_quotes";
    private static final int NUM_QUOTES = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotes);

        mQuotes = (TextView) this.findViewById(R.id.tv_quote);

        Bundle bundleLoader = new Bundle();
        bundleLoader.putInt(KEY_NUM_QUOTES, NUM_QUOTES);
        Loader loaderQuotes = getSupportLoaderManager().initLoader(LOADER_ID, bundleLoader, this);
        loaderQuotes.forceLoad();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new LoaderQuotes(RandomQuotes.this, args.getInt(KEY_NUM_QUOTES));
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        for (String quote : data) {
            mQuotes.append(quote + "\n\n\n");
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }


    public static class LoaderQuotes extends AsyncTaskLoader<String[]> {
        private int numQuotes;
        private static final String method = "getQuote";
        private static final String format = "json";
        private static final String lang = "en";
        Context context;


        public LoaderQuotes(Context context, int numQuotes) {
            super(context);
            this.numQuotes = numQuotes;
            this.context = context;
        }

        @Override
        public String[] loadInBackground() {
/*
            Toast.makeText(this.getContext(), numQuotes + "", Toast.LENGTH_SHORT).show();
*/
            String[] quotes = new String[numQuotes];

            URL quoteRequestUrl = NetworkUtils.buildURL(method, format, lang);

            for (int i = 0; i < numQuotes; i++) {
                try {

                    String jsonQuote = NetworkUtils.getResponseFromHttpUrl(quoteRequestUrl);
                    quotes[i] = jsonQuote;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return quotes;
        }
    }
}
