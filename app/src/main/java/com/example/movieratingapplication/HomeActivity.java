package com.example.movieratingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<Film> filmList = new ArrayList<Film>();
        FilmAdapter filmAdapter = new FilmAdapter(this, filmList);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(filmAdapter);

        ImageView poster = (ImageView) findViewById(R.id.poster_list);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent posterIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(posterIntent);
            }
        });

    }
}