package com.example.movieratingapplication;

import android.os.AsyncTask;
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

public class QueryUtils {
    //Tag for the log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    private QueryUtils() {
    }

    public interface FilmProcessor {
        public void processFilms (Film film);
    }



    static class FilmAsyncTask extends AsyncTask<URL, Void, Film> {
        public FilmProcessor filmProcessor;


        public FilmAsyncTask (FilmProcessor processor) {
            this.filmProcessor = processor;

        }
        @Override
        protected Film doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl();

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create an {@link Film} object
            Film film = (Film) extractFeatureFromJson(jsonResponse).get(3);

            // Return the {@link Film} object as the result fo the {@link FilmAsyncTask}
            return film;
        }

        @Override
        protected void onPostExecute(Film film) {
            if (film == null) {
                return;
            }

            filmProcessor.processFilms(film);
        }

        private static URL createUrl() {
            URL url = null;
            try {
                url = new URL(QueryUtils.requestURL);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
            return url;
        }

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
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
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
                Log.e(LOG_TAG, "Problem retrieving the film JSON results.", e);
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
        public static Film parsingJsonObj (JSONObject currentFilm) {

            try {
                String title = currentFilm.getString("title");

                String imageUrl = currentFilm.getString("image");

                String country = currentFilm.getString("country");

                int year = currentFilm.getInt("year");

                String synopsis = currentFilm.getString("synopsis");

                String release = currentFilm.getString("release");

                JSONArray castCrew = currentFilm.getJSONArray("cast_crew");

                String director = castCrew.getJSONObject(0).getString("director");

                String dirImage = castCrew.getJSONObject(0).getString("dirImage");

                String mainAct = castCrew.getJSONObject(0).getString("cast1");

                String mainImage = castCrew.getJSONObject(0).getString("cast1Image");

                String supportAct = castCrew.getJSONObject(0).getString("cast2");

                String supportImage = castCrew.getJSONObject(0).getString("cast2Image");

                Film film = new Film(title, imageUrl, country, year, synopsis, release, director, dirImage, mainAct, mainImage, supportAct, supportImage);

                return film;


            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the film JSON results", e);
            }

            return null;
        }

        public static List<Film> extractFeatureFromJson(String filmJSON) {

            if (TextUtils.isEmpty(filmJSON)) {
                return null;
            }

            // Create an empty ArrayList that we can start adding films to
            ArrayList<Film> films = new ArrayList<>();


            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
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

    }
}

