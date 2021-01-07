package com.example.movieratingapplication;

import android.util.Log;

import com.google.gson.JsonArray;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImdbUtils {


    private static JsonArray filmList (String stringURL) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/get-most-popular-movies")
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.v("imdbUtils", "Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

