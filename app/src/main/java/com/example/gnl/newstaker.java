package com.example.gnl;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class newstaker {
    private static final String LOG_TAG = newstaker.class.getSimpleName();

    //constructor
    private newstaker() {
    }

    public static List<newslist> fetchnews(String requestUrl) {
        // URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem in HTTP request.", e);
        }
        // Extract relevant fields
        List<newslist> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String s) {
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem in the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String json = "";
        if (url == null) {
            return json;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //requestsuccessful
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                json = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response" + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                //close connection
                inputStream.close();
            }
        }
        return json;
    }

    private static String readFromStream(InputStream i) throws IOException {
        StringBuilder output = new StringBuilder();
        if (i != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(i, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<newslist> extractFeatureFromJson(String NewsJSON) {
        if (TextUtils.isEmpty(NewsJSON)) {
            return null;
        }
        List<newslist> newsList = new ArrayList<>();
        // Try to parse the JSON response string.
        try {
            // Create JSONObject
            JSONObject baseJsonResponse = new JSONObject(NewsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject current = resultsArray.getJSONObject(i);
                String Title = current.getString("webTitle");
                String category = current.getString("sectionName");
                String date = current.getString("webPublicationDate");
                String url = current.getString("webUrl");
                String type = current.getString("type");
                date = date.replaceAll("[a-zA-Z]", " ");
                newsList.add(new newslist(Title, category, date, url, type));
            }
        } catch (JSONException e) {
            Log.e("newstaker", "Problem parsing", e);
        }

        return newsList;
    }
}
