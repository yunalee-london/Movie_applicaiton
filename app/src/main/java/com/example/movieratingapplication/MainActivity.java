package com.example.movieratingapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;

import static com.example.movieratingapplication.QueryUtils.*;


public class MainActivity extends AppCompatActivity {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilmAsyncTask task = new FilmAsyncTask();
        task.execute();
    }

}



//        TextView releaseDate = findViewById(R.id.release);
//        LocalDate rel_date = LocalDate.of(2017, 01, 13);
//        releaseDate.setText(DateCalculation.findDifference(rel_date, LocalDate.now()));






