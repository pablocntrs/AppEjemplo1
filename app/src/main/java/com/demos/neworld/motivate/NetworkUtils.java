package com.demos.neworld.motivate;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Pablo LK on 11/10/2017.
 */

public class NetworkUtils {
    private static final String dirBase = "http://api.forismatic.com/api/1.0/";
    private static final String paramMethod = "method";
    private static final String paramFormat = "format";
    private static final String paramLang = "lang";

    public static URL buildURL(String method, String format, String lang) {

        Uri builtURI = Uri.parse(dirBase).buildUpon()
                .appendQueryParameter(paramMethod, method)
                .appendQueryParameter(paramFormat, format)
                .appendQueryParameter(paramLang, lang)
                .build();

        URL url=null;
        try {
            url = new URL(builtURI.toString());
        } catch (MalformedURLException mfe) {
            Log.v("", "Built URI "+url);
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
