/*
package com.example.movieratingapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class FilmActivity<clickListener> extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    class FilmInterface implements QueryUtils.FilmProcessor {

        @Override
        public void processFilms(Film film) {
            updateUi(film);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        FilmInterface filmInterface = new FilmInterface();
        QueryUtils.FilmAsyncTask task = new QueryUtils.FilmAsyncTask(filmInterface);
        task.execute();


        //find the view of home icon
        ImageView homeIcon = (ImageView) findViewById(R.id.home);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(FilmActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        ImageView searchIcon = (ImageView) findViewById(R.id.search);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(FilmActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        ImageView uploadIcon = (ImageView) findViewById(R.id.upload);
        uploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(FilmActivity.this, UploadActivity.class);
                startActivity(uploadIntent);
            }
        });
    }


    public void updateUi(Film film) {
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(film.getTitle());

        TextView synopTextView = findViewById(R.id.synopsis);
        synopTextView.setText(film.getSynopsis());

        TextView relDateTextView = findViewById(R.id.releasedate);
        relDateTextView.setText("Release Date: " + film.getRelease());

        TextView dirTextView = findViewById(R.id.director);
        dirTextView.setText(film.getDir() + "\nDirector");

        TextView mainTextView = findViewById(R.id.main);
        mainTextView.setText(film.getMain());

        TextView supportTextView = findViewById(R.id.support);
        supportTextView.setText(film.getSupport());

        TextView subTitleView = findViewById(R.id.subtitle);
        subTitleView.setText(film.getMain()+ " & " + film.getSupport()+", " + film.getCountry()+ ", "+film.getYear());

        ImageView posterView = findViewById(R.id.imageUrl);
        String imageUrl = film.getImage();
        Picasso.get().load(imageUrl).into(posterView);

        ImageView dirView = findViewById(R.id.dirImage);
        String dirImageUrl = film.getDirImage();
        Picasso.get().load(dirImageUrl).into(dirView);

        ImageView mainView = findViewById(R.id.mainImage);
        String mainImageUrl = film.getMainImage();
        Picasso.get().load(mainImageUrl).into(mainView);

        ImageView supportView = findViewById(R.id.supportImage);
        String suppImageUrl = film.getSupportImage();
        Picasso.get().load(suppImageUrl).into(supportView);
    }



}


//        TextView releaseDate = findViewById(R.id.release);
//        LocalDate rel_date = LocalDate.of(2017, 01, 13);
//        releaseDate.setText(DateCalculation.findDifference(rel_date, LocalDate.now()));






*/
