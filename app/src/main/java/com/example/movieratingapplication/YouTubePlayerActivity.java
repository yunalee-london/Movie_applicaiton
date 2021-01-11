package com.example.movieratingapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayerActivity extends YouTubeBaseActivity {
    private static final String API_KEY = "AIzaSyDhobepN1rZAuOkoBdyNYw0cpkmgFHFQcQ";
    YouTubePlayerView mYouTubePlayerView;
    Button mPlayButton;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    String mVideoId;
    private static final String LOG_TAG = YouTubePlayerActivity.class.getSimpleName();
    private static final String YOUTUBE_ID = "CDQH0t6JiS4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Log.v(LOG_TAG, "onCreate: Starting");

       /* Intent intent = getIntent();
        mVideoId = intent.getStringExtra("videoId");*/


        mPlayButton = (Button) findViewById(R.id.play_button);
        mYouTubePlayerView = findViewById(R.id.youtubeplayer);


        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                Log.v(LOG_TAG, "onClick: Done initializing");

                    youTubePlayer.loadVideo(YOUTUBE_ID);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.v(LOG_TAG, "onClick: Failed to initialize");
            }
        };

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "onClick: Initializing YouTube Player");

                mYouTubePlayerView.initialize(API_KEY, mOnInitializedListener);

            }
        });



    }

    /*private class VideoAsyncTask extends AsyncTask<> {
        @Override
        protected String doInBackground(String... urls) {

            String videoId = null;
            Intent intent = getIntent();
            String imdbId = intent.getParcelableExtra("imdb");
            videoId = OldFilmUtils.getVideoId(imdbId);

            return videoId;
        }

        @Override
        protected void onPostExecute(List<ContentValues> films) {

            Log.v(MainActivity.class.getSimpleName(), "completed writing to db");

        }
    }*/
}