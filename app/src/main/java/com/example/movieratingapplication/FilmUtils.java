package com.example.movieratingapplication;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FilmUtils {
    private static final String LOG_TAG = FilmUtils.class.getSimpleName();
    private static final String requestURL = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt7126948";
    //private static final String testURL = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt7126948";
    private static final String allFilmIdURL = "https://imdb8.p.rapidapi.com/title/get-most-popular-movies";

    private static String makeHttpRequest(String stringURL) throws IOException {
        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(stringURL)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();
        Log.v(LOG_TAG, "-----------------------------Response: " + jsonResponse);
        return jsonResponse;
    }


    /*private static List<ContentValues> extractFeatureFromJson(String filmJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(filmJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding films to
        List<ContentValues> filmValues = new ArrayList<>();

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string


            JSONArray filmArray = new JSONArray(filmJSON);


            for (int i = 0; i < filmArray.length(); i++) {

                JSONObject currentFilm = filmArray.getJSONObject(i);

                ContentValues values = parsingJsonObj(currentFilm);

                filmValues.add(values);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the film JSON results", e);
        }

        // Return the list of films
        Log.v("------------------------------OldFilmUtils", "films: " + filmValues);
        return filmValues;
    }*/

    private static String mostPopularFilms (String stringURL) {
        String filmIdList = null;
        try {
            filmIdList = makeHttpRequest(allFilmIdURL);
            Log.v(LOG_TAG, "Film Id JsonArray: " + filmIdList);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return filmIdList;
    }




/*
        private static ContentValues parsingJsonObj(JSONObject currentFilm) {

        try {
            int id = 0;

            String title = currentFilm.getString("title");

            String poster = currentFilm.getString("image");

            String country = currentFilm.getString("country");

            String year = currentFilm.getString("year");

            String synopsis = currentFilm.getString("synopsis");

            String releaseDate = currentFilm.getString("release");

            String director = currentFilm.getString("director");

            String dirPic = currentFilm.getString("dirImage");

            String mainAct = currentFilm.getString("main");

            String mainPic = currentFilm.getString("mainImage");

            String supportAct = currentFilm.getString("support");

            String supportPic = currentFilm.getString("supportImage");

            Film film = new Film(id, title, poster, country, year, synopsis, releaseDate,
                    director, dirPic, mainAct, mainPic, supportAct, supportPic);


            ContentValues values = new ContentValues();
            values.put(FilmContract.FilmEntry.COLUMN_TITLE, title);
            values.put(FilmContract.FilmEntry.COLUMN_COUNTRY, country);
            values.put(FilmContract.FilmEntry.COLUMN_IMAGE_URL, poster);
            values.put(FilmContract.FilmEntry.COLUMN_YEAR, year);
            values.put(FilmContract.FilmEntry.COLUMN_SYNOPSIS, synopsis);
            values.put(FilmContract.FilmEntry.COLUMN_RELEASE, releaseDate);
            values.put(FilmContract.FilmEntry.COLUMN_DIRECTOR, director);
            values.put(FilmContract.FilmEntry.COLUMN_DIR_URL, dirPic);
            values.put(FilmContract.FilmEntry.COLUMN_MAIN, mainAct);
            values.put(FilmContract.FilmEntry.COLUMN_MAIN_URL, mainPic);
            values.put(FilmContract.FilmEntry.COLUMN_SUPPORT, supportAct);
            values.put(FilmContract.FilmEntry.COLUMN_SUPPORT_URL, supportPic);
            Log.v(MainActivity.class.getSimpleName(), "Film; " + values.toString());
            return values;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the film JSON results", e);
        }

        return null;
    }


    public static List<ContentValues> fetchFilmData(String requestUrl) {
        // Create URL object
        //URL url = createUrl(testURL);


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<ContentValues> films = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link filmValues}s
        return films;
    }*/

}


  /*private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(testURL);
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
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
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
        Log.v(LOG_TAG, "JsonResponse-------------------:" + jsonResponse);
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }*/

    /*public static ArrayList<String> castIdList(String stringURL) throws IOException, JSONException {
        ArrayList<String> castIdList = new ArrayList<String>();
        String jsonResponse = null;
        ArrayList<String> filmIdList = mostPopularFilmIdList(allFilmIdURL);

        for (int i = 0; i < filmIdList.size(); i++) {
            jsonResponse = makeHttpRequest(castIdUrl + filmIdList.get(i));

            JSONArray idJsonArray = new JSONArray(jsonResponse);

            for (int j = 0; j < 2; i++) {
                String[] id = idJsonArray.getString(i).split("/");
                castIdList.add(id[2]);
            }
        }

        Log.v(LOG_TAG, "--------------------------Cast Id Only Array: " + castIdList);
        return castIdList;*/