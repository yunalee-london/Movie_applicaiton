package com.example.movieratingapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Film> filmArray = new ArrayList<>();
        FilmAdapter filmAdapter = new FilmAdapter(this, filmArray);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(filmAdapter);

        //when the imageview is clicked it directs to mainacitvity
    /*ImageView poster = (ImageView) findViewById(R.id.poster_list);
    poster.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent posterIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(posterIntent);
        }
    });*/



    }

}
