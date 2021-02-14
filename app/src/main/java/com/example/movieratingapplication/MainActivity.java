package com.example.movieratingapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.movieratingapplication.data.FilmContract;
import com.example.movieratingapplication.data.FilmDbHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //private FilmAdapter filmAdapter;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //private static final String requestURL = "https://my-movie-rating.herokuapp.com/";
    private static final String requestURL = "http://10.0.2.2:3001/";
    private static final String testURL = "https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt7126948";
    private static final String allFilmIdURL = "https://imdb8.p.rapidapi.com/title/get-most-popular-movies";

    private FilmDbHelper filmDbHelper;
    //initialize the loader
    private static final int FILM_LOADER = 0;
    //make it global variable
    FilmCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });*/
        //Find the ListView which will be populated with the film data
        ListView filmListView = (ListView) findViewById(R.id.list_view);

        //Find and set empty view on the Listview, so that it only shows when the list has 0 item.
        View emptyView = findViewById(R.id.empty_view);
        filmListView.setEmptyView((emptyView));


        //Setup the Adapter to create a list item for each row of film data in the cursor.
        //there is no film data yet (until the loader finishes) so pass in null for the cursor.
        mCursorAdapter = new FilmCursorAdapter(this, null);
        filmListView.setAdapter(mCursorAdapter);


        //Kick off the loader
        LoaderManager.getInstance(this).initLoader(FILM_LOADER, null, this);

        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                int currentFilmId = cursor.getColumnIndex(FilmContract.FilmEntry._ID);
                int imdbColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_IMDB);
                int titleColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_TITLE);
                int imageUrlColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_IMAGE_URL);
                int mainColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN);
                int supportColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT);
                int countryColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_COUNTRY);
                int yearColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_YEAR);
                int synopColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SYNOPSIS);
                int relColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_RELEASE);
                int dirColumnIndex = cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIRECTOR);
                int dirUrlColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_DIR_URL);
                int mainUrlColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_MAIN_URL);
                int suppUrlColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_SUPPORT_URL);
                int videoIdColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_VIDEO_ID);
                int videoUrlColumnIndex =
                        cursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_VIDEO_URL);


                //Read the film attributes from the Cursor for current film
                long filmId = cursor.getLong(currentFilmId);
                String filmImdb = cursor.getString(imdbColumnIndex);
                String filmTitle = cursor.getString(titleColumnIndex);
                String filmImageUrl = cursor.getString(imageUrlColumnIndex);
                String filmMain = cursor.getString(mainColumnIndex);
                String filmSupport = cursor.getString(supportColumnIndex);
                String filmCountry = cursor.getString(countryColumnIndex);
                String filmYear = cursor.getString(yearColumnIndex);
                String filmRel = cursor.getString(relColumnIndex);
                String filmDir = cursor.getString(dirColumnIndex);
                String filmSynopsis = cursor.getString(synopColumnIndex);
                String filmDirUrl = cursor.getString(dirUrlColumnIndex);
                String filmMainUrl = cursor.getString(mainUrlColumnIndex);
                String filmSuppUrl = cursor.getString(suppUrlColumnIndex);
                String filmVideoId = cursor.getString(videoIdColumnIndex);
                String filmVideoUrl = cursor.getString(videoUrlColumnIndex);


                Film film = new Film(filmId, filmImdb, filmTitle, filmImageUrl, filmCountry, filmYear,
                        filmSynopsis, filmRel, filmDir, filmDirUrl, filmMain, filmMainUrl,
                        filmSupport, filmSuppUrl, filmVideoId, filmVideoUrl);
                Intent intent = new Intent(MainActivity.this, FilmActivity.class);
                intent.putExtra("film", film);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        FilmAsyncTask task = new FilmAsyncTask();
        task.execute(requestURL);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //initialize the loader
        //define a projection that specifies the columns from the table.
        String[] projection = {
                FilmContract.FilmEntry._ID,
                FilmContract.FilmEntry.COLUMN_IMDB,
                FilmContract.FilmEntry.COLUMN_TITLE,
                FilmContract.FilmEntry.COLUMN_IMAGE_URL,
                FilmContract.FilmEntry.COLUMN_MAIN,
                FilmContract.FilmEntry.COLUMN_SUPPORT,
                FilmContract.FilmEntry.COLUMN_COUNTRY,
                FilmContract.FilmEntry.COLUMN_YEAR,
                FilmContract.FilmEntry.COLUMN_DIR_URL,
                FilmContract.FilmEntry.COLUMN_DIRECTOR,
                FilmContract.FilmEntry.COLUMN_MAIN_URL,
                FilmContract.FilmEntry.COLUMN_SUPPORT_URL,
                FilmContract.FilmEntry.COLUMN_SYNOPSIS,
                FilmContract.FilmEntry.COLUMN_RELEASE,
                FilmContract.FilmEntry.COLUMN_VIDEO_ID,
                FilmContract.FilmEntry.COLUMN_VIDEO_URL,
        };

        //This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, FilmContract.FilmEntry.CONTENT_URI, projection, null, null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        //Update FilmCursorAdapter with this new cursor containing updated film data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);

    }

    private void saveFilmData(ContentValues values) {

        //Determine if this is a new or existing film
        // by checking if mCurrentFilmUri is null or not


        getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);


    }

    public class FilmAsyncTask extends AsyncTask<String, Void, List<ContentValues>> {

        @Override
        protected List<ContentValues> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                Log.e("MainActivity", "did not fetch anything");
                return null;
            }

            List<ContentValues> films = null;
            try {
                films = OldFilmUtils.fetchFilmData(allFilmIdURL);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            //Clear the table before loading it again.
            MainActivity.this.getContentResolver().delete(FilmContract.FilmEntry.CONTENT_URI,
                    null, null);


            // If there is a valid list of {@link filmValues}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            for (int i = 0; i < films.size(); i++) {

                ContentValues values = films.get(i);
                saveFilmData(values);

            }
            Log.v("MainActivity", "Number of films " + films.size());
            return films;
        }

        @Override
        protected void onPostExecute(List<ContentValues> films) {

            Log.v(MainActivity.class.getSimpleName(), "completed writing to db");

        }
    }
}
    


/*private void insertFilm() {
        //Create a ContentValues object where column names are the keys,
        //and the film's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_TITLE, "her");
        values.put(FilmContract.FilmEntry.COLUMN_COUNTRY, "USA");
        values.put(FilmContract.FilmEntry.COLUMN_IMAGE_URL, "https://upload.wikimedia" +
                ".org/wikipedia/en/4/44/Her2013Poster.jpg");
        values.put(FilmContract.FilmEntry.COLUMN_YEAR, "2013");
        values.put(FilmContract.FilmEntry.COLUMN_SYNOPSIS, "In a near future, a lonely writer " +
                "develops an unlikely relationship with an operating system designed to meet his " +
                "every need.");
        values.put(FilmContract.FilmEntry.COLUMN_RELEASE, "2014.02.14");
        values.put(FilmContract.FilmEntry.COLUMN_DIRECTOR, "Spike Jonze");
        values.put(FilmContract.FilmEntry.COLUMN_DIR_URL, "https://m.media-amazon" +
                ".com/images/M/MV5BMjE3MDkyNTMzNl5BMl5BanBnXkFtZTcwOTAxOTAyMw@@._V1_UY317_CR22,0," +
                "214,317_AL_.jpg");
        values.put(FilmContract.FilmEntry.COLUMN_MAIN, "Joaquin Phoenix");
        values.put(FilmContract.FilmEntry.COLUMN_MAIN_URL, "https://m.media-amazon" +
                ".com/images/M" +
                "/MV5BZGMyY2Q4NTEtMWVkZS00NzcwLTkzNmQtYzBlMWZhZGNhMDhkXkEyXkFqcGdeQXVyNjk1MjYyNTA" +
                "@._V1_UX214_CR0,0,214,317_AL_.jpg");
        values.put(FilmContract.FilmEntry.COLUMN_SUPPORT, "Scarlett Johansson");
        values.put(FilmContract.FilmEntry.COLUMN_SUPPORT_URL, "https://m.media-amazon" +
                ".com/images/M/MV5BMTM3OTUwMDYwNl5BMl5BanBnXkFtZTcwNTUyNzc3Nw@@._V1_UY317_CR23,0," +
                "214,317_AL_.jpg");

        Uri newUri = getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        *//*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); *//*// Do not iconify the widget; expand it by
        // default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertFilm();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
            case R.id.action_search:


        }
        return super.onOptionsItemSelected(item);
    }
*/



