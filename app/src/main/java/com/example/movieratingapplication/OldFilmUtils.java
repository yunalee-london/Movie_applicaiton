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
    private static final String TOP_RATED_FILMS_URL = "https://imdb8.p.rapidapi.com/title/get-top-rated-movies";
    private static final String filmOverviewUrl = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=";
    private static final String VIDEO_ID_URL = "https://imdb8.p.rapidapi.com/title/get-videos?tconst=";
    private static final String CAST_ID_URL = "https://imdb8.p.rapidapi.com/title/get-top-cast?tconst=";
    private static final String MAIN_ACT_URL = "https://imdb8.p.rapidapi.com/actors/get-bio?nconst=";
    private static final String CREW_URL = "https://imdb8.p.rapidapi.com/title/get-top-crew?tconst=";
    private static final String PREVIEW_URL = "https://imdb8.p.rapidapi.com/title/get-video-playback?viconst=";



    public static String getVideoId (String imdbId) throws IOException, JSONException {
        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(VIDEO_ID_URL+imdbId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();

        JSONObject videoResponse = new JSONObject(jsonResponse);
        JSONObject resourceObj = videoResponse.getJSONObject("resource");
        JSONArray videosArray = resourceObj.getJSONArray("videos");
        JSONObject firstVideo = (JSONObject) videosArray.get(0);
        String[] videoIdParsing = firstVideo.getString("id").split("/");
        String videoId = videoIdParsing[2];


        Log.v(LOG_TAG, "Get Video Id: " + videoId);
        return videoId;
    }

    public static String getPreviewUrl (String videoId) throws IOException, JSONException {
        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(PREVIEW_URL+videoId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();

        JSONObject videoResponse = new JSONObject(jsonResponse);
        JSONObject resourceObj = videoResponse.getJSONObject("resource");
        JSONArray previewsArray = resourceObj.getJSONArray("previews");
        JSONObject firstPreview = (JSONObject) previewsArray.get(0);
        String previewUrl = firstPreview.getString("playUrl");
        Log.v(LOG_TAG, "Get Preview Url: " + previewUrl);

        return previewUrl;
    }

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
        return jsonResponse;
    }

    public static ArrayList<String> mostPopularFilmIdList () {
        ArrayList<String> filmIdList= new ArrayList<String>();
        String jsonResponse = null;
        try {
                jsonResponse = makeHttpRequest(allFilmIdURL);

            JSONArray idJsonArray = new JSONArray(jsonResponse);

            for (int i=0; i < 5; i++) {
                String[] id = idJsonArray.getString(i).split("/");
                filmIdList.add(id[2]);
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        Log.v(LOG_TAG, "Get Most Popular Film Id: " + filmIdList);
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
        Log.v(LOG_TAG, "Film Values: " + filmValues);
        return filmValues;
    }

    private static ContentValues parsingJsonObj(JSONObject currentFilm) throws IOException {

        try {
            String[] idParsing = currentFilm.getString("id").split("/");

            String imdb = idParsing[2];

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


            JSONObject directorJson = getDirectorJson(imdb);

            JSONObject directorObj = (JSONObject) directorJson.getJSONArray("directors").get(0);

            String director = directorObj.getString("name");

            JSONObject imageDirObj = directorObj.getJSONObject("image");

            String dirPic = imageDirObj.getString("url");


            JSONObject mainJson = getMainActJson(imdb);

            String mainAct = mainJson.getString("name");

            JSONObject mainImageJson = mainJson.getJSONObject("image");

            String mainPic = mainImageJson.getString("url");


            JSONObject supportActJson = getSupportActJson(imdb);

            String supportAct = supportActJson.getString("name");

            JSONObject supportImageJson = supportActJson.getJSONObject("image");

            String supportPic = supportImageJson.getString("url");

            String videoId = getVideoId(imdb);

            String videoUrl = getPreviewUrl(videoId);
            Log.v(LOG_TAG, "Parsed PREVIEW ID: " + videoUrl);


            Film film = new Film(id, imdb, title, poster, country, year, synopsis, releaseDate,
                    director, dirPic, mainAct, mainPic, supportAct, supportPic, videoId, videoUrl);



            ContentValues values = new ContentValues();
            values.put(FilmContract.FilmEntry.COLUMN_IMDB, imdb);
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
            values.put(FilmContract.FilmEntry.COLUMN_VIDEO_ID, videoId);
            values.put(FilmContract.FilmEntry.COLUMN_VIDEO_URL, videoUrl);
            Log.v(LOG_TAG, "FilmValues : " + values.toString());
            return values;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the film JSON results", e);
        }

        return null;
    }

    private static ArrayList<String> getTopCastIds(String imdbId) throws IOException,
            JSONException {
        ArrayList<String> castIdList = new ArrayList<>();

        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CAST_ID_URL + imdbId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();

        JSONArray castIdJsonArray = new JSONArray(jsonResponse);
        for (int i=0; i<2; i++) {
            String[] castId = castIdJsonArray.getString(i).split("/");
            castIdList.add(castId[2]);
        }
        Log.v(LOG_TAG, "castIdList: " + castIdList);
        return castIdList;

    }

    private static JSONObject getMainActJson (String imdbId) throws IOException, JSONException {
        String mainId = getTopCastIds(imdbId).get(0);

        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(MAIN_ACT_URL + mainId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();
        JSONObject mainActData = new JSONObject(jsonResponse);
        Log.v(LOG_TAG, "MAIN ACT JSON: " + mainActData);
        return mainActData;

    }

    private static JSONObject getSupportActJson (String imdbId) throws IOException, JSONException {
        String supportId = getTopCastIds(imdbId).get(1);

        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(MAIN_ACT_URL + supportId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();
        JSONObject suppActData = new JSONObject(jsonResponse);
        Log.v(LOG_TAG, "SUPPORT ACT JSON: " + suppActData);

        return suppActData;

    }

    private static JSONObject getDirectorJson (String filmId) throws IOException, JSONException {
        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CREW_URL+filmId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();

        JSONObject directorData = new JSONObject(jsonResponse);

        return directorData;

    }



    public static List<ContentValues> fetchFilmData(String requestUrl) throws IOException,
            JSONException {
        ArrayList<String> filmIdList = mostPopularFilmIdList();
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

