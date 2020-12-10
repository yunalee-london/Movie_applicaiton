package com.example.movieratingapplication;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.example.movieratingapplication.QueryUtils.FilmAsyncTask;
import static com.example.movieratingapplication.QueryUtils.FilmProcessor;


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
        relDateTextView.setText("Release Date: " + film.getRelease());

        TextView dirTextView = findViewById(R.id.director);
        dirTextView.setText(film.getDir());

        TextView mainTextView = findViewById(R.id.main);
        mainTextView.setText(film.getMain());

        TextView supportTextView = findViewById(R.id.support);
        supportTextView.setText(film.getSupport());

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






