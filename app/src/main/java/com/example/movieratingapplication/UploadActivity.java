package com.example.movieratingapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadActivity extends AppCompatActivity {
    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Button add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClickHttpPost httpPost= new onButtonClickHttpPost();
                httpPost.sendPost();
        }
    });

    }
    public class onButtonClickHttpPost {
        public void sendPost() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        Gson gson = new Gson();
                        String json = gson.toJson(submitFilm());

                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(json);


                        os.flush();
                        os.close();

                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG" , conn.getResponseMessage());

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

        private Film submitFilm() {
            EditText titleField = (EditText) findViewById(R.id.title_field);
            String title = titleField.getText().toString();
            Log.v("SearchActivity", "Title: " + title);

            EditText countryField = (EditText) findViewById(R.id.country_field);
            String country = countryField.getText().toString();

            EditText dirField = (EditText) findViewById(R.id.director_field);
            String director = dirField.getText().toString();

            EditText mainField = (EditText) findViewById(R.id.main_field);
            String mainAct = mainField.getText().toString();

            EditText suppField = (EditText) findViewById(R.id.support_field);
            String supportAct = suppField.getText().toString();

            EditText synopField = (EditText) findViewById(R.id.synopsis_field);
            String synopsis = synopField.getText().toString();

            EditText posterField = (EditText) findViewById(R.id.poster_url_field);
            String poster = posterField.getText().toString();

            EditText dirUrl = (EditText) findViewById(R.id.dir_url_field);
            String dirPic = dirUrl.getText().toString();

            EditText mainUrl = (EditText) findViewById(R.id.main_url_field);
            String mainPic = mainUrl.getText().toString();

            EditText supportUrl = (EditText) findViewById(R.id.support_url_field);
            String supportPic = supportUrl.getText().toString();

            DatePicker dateField = (DatePicker) findViewById(R.id.date_field);
            int yearInt = dateField.getYear();
            String year = Integer.toString(dateField.getYear());
            String month = Integer.toString(dateField.getMonth());
            String date = Integer.toString(dateField.getDayOfMonth());
            String releaseDate = year + "." + month + "." + date;

            Film newFilm = new Film(title, poster, country, yearInt, synopsis, releaseDate, director, dirPic, mainAct, mainPic, supportAct, supportPic);
            return newFilm;
        }
    }
}