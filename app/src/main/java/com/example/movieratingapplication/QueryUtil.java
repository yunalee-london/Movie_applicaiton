/*
package com.example.movieratingapplication;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    private static final String LOG_TAG = QueryUtil.class.getSimpleName();
    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(requestURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    */
/**
     * Make an HTTP request to the given URL and return a String as the response.
     *//*

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 */
/* milliseconds *//*
);
            urlConnection.setConnectTimeout(15000 */
/* milliseconds *//*
);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    */
/**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     *//*

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Film> extractFeatureFromJson(String filmJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(filmJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Film> films = new ArrayList<>() ;

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string


            JSONArray filmArray = new JSONArray(filmJSON);


            for (int i = 0; i < filmArray.length(); i++) {

                JSONObject currentFilm = filmArray.getJSONObject(i);

                Film film = parsingJsonObj(currentFilm);

                films.add(film);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the film JSON results", e);
        }

        // Return the list of films
        Log.v("------------------------------QueryUtils", "films: " + films);
        return films;
    }

    private static Film parsingJsonObj (JSONObject currentFilm) {

        try {
            String title = currentFilm.getString("title");

            String imageUrl = currentFilm.getString("image");

            String country = currentFilm.getString("country");

            String year = currentFilm.getString("year");

            String synopsis = currentFilm.getString("synopsis");

            String release = currentFilm.getString("release");

            String director = currentFilm.getString("director");

            String dirImage = currentFilm.getString("dirImage");

            String mainAct = currentFilm.getString("cast1");

            String mainImage = currentFilm.getString("cast1Image");

            String supportAct = currentFilm.getString("cast2");

            String supportImage = currentFilm.getString("cast2Image");

            Film film = new Film(title, imageUrl, country, year, synopsis, release, director, dirImage, mainAct, mainImage, supportAct, supportImage);

            return film;


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the film JSON results", e);
        }

        return null;
    }

    public static List<Film> fetchFilmData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Film> films = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return films;
    }

}
*/
