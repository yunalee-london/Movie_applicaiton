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

    class FilmInterface implements FilmProcessor {

        @Override
        public void processFilms(Film film) {
            updateUi(film);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilmInterface filmInterface = new FilmInterface();
        FilmAsyncTask task = new FilmAsyncTask(filmInterface);
        task.execute();
    }

    private void updateUi(Film film) {
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(film.getTitle());

        TextView synopTextView = findViewById(R.id.synopsis);
        synopTextView.setText(film.getSynopsis());

        TextView relDateTextView = findViewById(R.id.releasedate);
        relDateTextView.setText(film.getRelease());

        TextView dirTextView = findViewById(R.id.director);
        dirTextView.setText(film.getDir());

        TextView mainTextView = findViewById(R.id.main);
        mainTextView.setText(film.getMain());

        TextView supportTextView = findViewById(R.id.support);
        supportTextView.setText(film.getSupport());
    }



}



//        TextView releaseDate = findViewById(R.id.release);
//        LocalDate rel_date = LocalDate.of(2017, 01, 13);
//        releaseDate.setText(DateCalculation.findDifference(rel_date, LocalDate.now()));






