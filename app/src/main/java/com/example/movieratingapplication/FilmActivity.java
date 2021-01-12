package com.example.movieratingapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity {
    Uri mCurrentFilmUri;
    private String mCurrentFilmImdb;
    private String mVideoId;
    private String mVideoUrl;
    private static final String requestURL = "http://10.0.2.2:3001/";

    private static final String LOG_TAG = FilmActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        FloatingActionButton playFab = (FloatingActionButton) findViewById(R.id.playFab);
        playFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilmActivity.this, VideoActivity.class);
                intent.putExtra("videoUrl", mVideoUrl);
                Log.v(VideoActivity.class.getSimpleName(), "Video Intent: " + mVideoUrl);
                startActivity(intent);
            }

        });

        Intent intent = getIntent();
        Film film = intent.getParcelableExtra("film");

        mCurrentFilmImdb = film.getImdb();

        mVideoId = film.getVideoId();

        mVideoUrl = film.getVideoUrl();

        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(film.getTitle());

        TextView synopTextView = findViewById(R.id.synopsis);
        synopTextView.setText(film.getSynopsis());

        TextView relDateTextView = findViewById(R.id.releasedate);
        relDateTextView.setText("Release Date: " + film.getRelease());

        TextView dirTextView = findViewById(R.id.director);
        dirTextView.setText(film.getDirector() + "\nDirector");

        TextView mainTextView = findViewById(R.id.main);
        mainTextView.setText(film.getMain());

        TextView supportTextView = findViewById(R.id.support);
        supportTextView.setText(film.getSupport());

        TextView subTitleView = findViewById(R.id.subtitle);
        subTitleView.setText(film.getMain() + " & " + film.getSupport() + ", " + film.getCountry() + ", " + film.getYear());

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


    /*@Override
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
        long id = (long) film.getId();
        Uri uri = ContentUris.withAppendedId(FilmContract.FilmEntry.CONTENT_URI, id);
        mCurrentFilmUri = uri;

        switch (item.getItemId()) {

            case R.id.action_update:

                Intent newIntent = new Intent(FilmActivity.this, UploadActivity.class);


                Log.v(LOG_TAG, "uri--------------------------------:" + mCurrentFilmUri);
                newIntent.setData(mCurrentFilmUri);

                startActivity(newIntent);

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_a_film:
                Film filmToDelete = new Film(film.getId(), film.getImdb(), film.getTitle(), film.getImage(), film.getCountry(), film.getYear(), film.getSynopsis(), film.getRelease(), film.getDirector(), film.getDirImage(), film.getMain(), film.getMainImage(), film.getSupport(), film.getSupportImage(), film.getVideoId());
                onButtonClickHttpDelete httpDelete = new onButtonClickHttpDelete();
                httpDelete.sendDelete(filmToDelete);
                *//*
                int rowsDeleted = getContentResolver().delete(mCurrentFilmUri, null, null);

                if (rowsDeleted == 0) {
                    Toast.makeText(this, "deleting the film failed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Film deleted successfully", Toast.LENGTH_SHORT).show();
                }*//*
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public class onButtonClickHttpDelete {
        public void sendDelete(Film film) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request.Builder builder = null;
                    try {

                        Gson gson = new Gson();
                        String json = gson.toJson(film);
                        Log.v(LOG_TAG, "json :" + json);

                        builder = new Request.Builder()
                                .url(new URL(requestURL))
                                .delete(RequestBody.create(
                                        MediaType.parse("application/json; charset=utf-8"),
                                        json));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    Request request = builder.build();

                    try {
                        Response response = client.newCall(request).execute();
                        String string = response.body().string();
                        // TODO use your response
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    /* try {
                        URL url = new URL(requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("DELETE");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        conn.connect();



                        Gson gson = new Gson();
                        String json = gson.toJson(film);
                        Log.v(LOG_TAG, "json :" + json);

                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(json);


                        os.flush();
                        os.close();

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            /*});

            thread.start();
        }
    }*/



