package com.example.movieratingapplication;

import android.content.ContentValues;
import android.util.Log;

import com.example.movieratingapplication.data.FilmContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OldFilmUtils {
    private static final String LOG_TAG = OldFilmUtils.class.getSimpleName();
    private static final String requestURL = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt7126948";
    //private static final String testURL = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt7126948";
    private static final String allFilmIdURL = "https://imdb8.p.rapidapi.com/title/get-most-popular-movies";
    private static final String filmOverviewUrl = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=";
    private static final String VIDEO_ID_URL = "https://movies-tvshows-data-imdb.p.rapidapi.com/?type=get-movie-details&imdb=";

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
        Log.v(LOG_TAG, "-----------------------------JsonResponse: " + jsonResponse);
        return jsonResponse;
    }

    private static String getVideoId (String filmId) {
        String videoId = "";
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(VIDEO_ID_URL + filmId);

            JSONObject video_response = new JSONObject(jsonResponse);
            videoId = video_response.getString("youtube_trailer_key");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoId;
    }

    public static ArrayList<String> mostPopularFilmIdList (String stringURL) {
        ArrayList<String> filmIdList= new ArrayList<String>();
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(allFilmIdURL);

            JSONArray idJsonArray = new JSONArray(jsonResponse);

            for (int i=0; i < 1; i++) {
                String[] id = idJsonArray.getString(i).split("/");
                filmIdList.add(id[2]);
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        Log.v(LOG_TAG, "--------------------------Film Id Only Array: " + filmIdList);
        return filmIdList;
    }


    private static List<ContentValues> extractFeatureFromJson(JSONArray filmJSON) {
        // If the JSON string is empty or null, then return early.
        if (filmJSON.length() == 0) {
            return null;
        }

        // Create an empty ArrayList that we can start adding films to
        List<ContentValues> filmValues = new ArrayList<>();

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string


            for (int i = 0; i < filmJSON.length(); i++) {

                JSONObject currentFilm = filmJSON.getJSONObject(i);

                ContentValues values = parsingJsonObj(currentFilm);

                filmValues.add(values);

            }

        } catch (JSONException | IOException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the film JSON results", e);
        }

        // Return the list of films
        Log.v("------------------------------OldFilmUtils", "films: " + filmValues);
        return filmValues;
    }

    private static ContentValues parsingJsonObj(JSONObject currentFilm) throws IOException {

        try {
           /* String[] idParsing = currentFilm.getString("id").split("/");

            String id = idParsing[2];*/

            int id = 0;

            JSONObject titleJsonObj = currentFilm.getJSONObject("title");

            String title = titleJsonObj.getString("title");

            JSONObject imageJsonObj = titleJsonObj.getJSONObject("image");

            String poster = imageJsonObj.getString("url");

            JSONObject certificates = currentFilm.getJSONObject("certificates");

            String country = certificates.keys().next();

            String year = titleJsonObj.getString("year");

            JSONObject plotOutline = currentFilm.getJSONObject("plotOutline");

            String synopsis = plotOutline.getString("text");

            String releaseDate = currentFilm.getString("releaseDate");


            String director = "director";

            String dirPic = imageJsonObj.getString("url");

            String mainAct = "main";

            String mainPic = imageJsonObj.getString("url");

            String supportAct = "support";

            String supportPic = imageJsonObj.getString("url");

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
            Log.v(LOG_TAG, "Film; " + values.toString());
            return values;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the film JSON results", e);
        }

        return null;
    }


    public static List<ContentValues> fetchFilmData(String requestUrl) throws IOException,
            JSONException {
        ArrayList<String> filmIdList = mostPopularFilmIdList(requestUrl);
        JSONArray filmsJsonArray = new JSONArray();
        String jsonResponse = null;
        for (int i=0; i < filmIdList.size(); i++) {
            jsonResponse = makeHttpRequest(filmOverviewUrl+ filmIdList.get(i));
            //crewResponse = makeHttpRequest()
            JSONObject filmJsonObj = new JSONObject(jsonResponse);
            filmsJsonArray.put(filmJsonObj);

        } Log.v(LOG_TAG, "-----------------CurrentFilmJson: " + filmsJsonArray);


        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<ContentValues> films = extractFeatureFromJson(filmsJsonArray);

        // Return the list of {@link filmValues}s
        return films;
    }

}

