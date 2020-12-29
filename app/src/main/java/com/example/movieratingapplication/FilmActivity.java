package com.example.movieratingapplication;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieratingapplication.data.FilmContract;
import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity {
    Uri mCurrentFilmUri;

    private static final String LOG_TAG= FilmActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Intent intent = getIntent();
        Film film = intent.getParcelableExtra("film");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_filmactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        Intent intent = getIntent();
        Film film = intent.getParcelableExtra("film");
        int id = (int) film.getId();
        Uri uri = ContentUris.withAppendedId(FilmContract.FilmEntry.CONTENT_URI, id);
        mCurrentFilmUri = uri;

        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_update:

                Intent newIntent = new Intent(FilmActivity.this, UploadActivity.class);


                Log.v(LOG_TAG, "uri--------------------------------:" +mCurrentFilmUri);
                newIntent.setData(mCurrentFilmUri);

                startActivity(newIntent);

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_a_film:

                int rowsDeleted = getContentResolver().delete(mCurrentFilmUri, null, null);

                if (rowsDeleted ==0) {
                    Toast.makeText(this, "deleting the film failed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Film deleted successfully", Toast.LENGTH_SHORT).show();
                }
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
