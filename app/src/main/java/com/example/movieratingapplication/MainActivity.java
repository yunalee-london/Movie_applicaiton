package com.example.movieratingapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FilmAdapter filmAdapter;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String requestURL = "https://my-movie-rating.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filmAdapter = new FilmAdapter(this, new ArrayList<Film>());

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(filmAdapter);

        FilmAsyncTask task = new FilmAsyncTask();
        task.execute(requestURL);
    }

    private class FilmAsyncTask extends AsyncTask<String, Void, List<Film>> {

        @Override
        protected List<Film> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                Log.e("MainAcitivity", "did not fetch anything");
                return null;
            }

            List<Film> films = QueryUtil.fetchFilmData(requestURL);
            return films;
        }

        @Override
        protected void onPostExecute(List<Film> films) {
            // Clear the adapter of previous earthquake data
            filmAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (films != null && !films.isEmpty()) {
                filmAdapter.addAll(films);
            }
        }
    }
}




//when the imageview is clicked it directs to mainacitvity
    /*ImageView poster = (ImageView) findViewById(R.id.poster_list);
    poster.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent posterIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(posterIntent);
        }
    });*/

        /*listView.setOnItemClickListener((adapterView, view, position, l) -> {
            // Find the current earthquake that was clicked on
            Film currentFilm = mAdapter.getItem(position);

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            Uri filmUri = Uri.parse(currentFilm.getUrl());

            // Create a new intent to view the earthquake URI
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, filmUri);

            // Send the intent to launch a new activity
            startActivity(websiteIntent);
        });*/
