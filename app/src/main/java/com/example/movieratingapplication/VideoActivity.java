package com.example.movieratingapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoActivity extends AppCompatActivity {
    private static final String VIDEO_SAMPLE =
            //"https://imdb8.p.rapidapi.com/title/get-videos?tconst="
            //"https://developers.google.com/training/images/tacoma_narrows.mp4";
            //"https://imdb-video.media-imdb.com/vi3240214809/1434659607842-pgv4ql-1607305868042.mp4?Expires=1610298386&Signature=RM6QEJ3jagk9al7EOERT48nfLt3t~DXFsI72zUsfRzSV~cBQ1hyoiX~6dqMi6JbLc7Nz7C1lWCkzdmwNwcQJIK1HwJEZJDY4ERSS~nG~u96qNua5CCbLgb2l1U07KvLDF~umtpjZqo-2OYvGpp-jqKuiZSamYGHgNr-FslVs4iwCoQOqxsSLdVAXH2Cr0jYIlskH88AB54oYRRBfpaDhkM~n~sYuXupKuHPzhMTvTgrnXhq6fT07An6HSt5OiWmd8lmGxzv~6cWeRv9tE~b9zDQJHh31-oolgK-~lv1bxuuyAD7kNSEWyiomEMFL~dehMwYx0lYddcW6blPukaNx4g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA";
            "https://imdb-video.media-imdb.com/vi1015463705/hls-preview-b76ce5d6-075e-4e6d-b83b-97ed40917555.m3u8?Expires=1610467408&Signature=R4dXdzopeMgr7brzjyMYcviDhh3Kj7f1h8JMTUsSyQDcUJQaXigMKQjxeRQwCuqb7OZ4EET6FcGdPrtxItmlsGlsF3oQEDojsH~Fgu5WZt6FO-7osFUkhCNAR0WNhGpg5QzUif69xuth4rdw8Djujx5RaCT4NOGJyTPkIZHSH1xDUUdEp2hcsEFok~NIaRJIn6Rw~kHyZwGJxKx26JKd9WBVbiRsZ8Q0rMn7ETWNZYYcPuqvkdTeHBY0MAWWPAhpZFV030zzgTdTBrgrXMzvEQnZ6HefIwEk1jDyCiBpz5f3gk1vddVbUyjeAO8TEED-IN71qa2gAT~Y4GPya2f23A__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA";
    private static final String LOG_TAG = VideoActivity.class.getSimpleName();
    private VideoView mVideoView;
    private String mVideoId;
    private int mCurrentPosition = 0; //playback position is recorded in milliseconds from 0.
    private static final String PLAYBACK_TIME = "play_time";
    private TextView mBufferingTextView;
    private String mPreviewUrl;
    private static final String PREVIEW_URL = "https://imdb8.p.rapidapi.com/title/get-video-playback?viconst=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mBufferingTextView = findViewById(R.id.buffering_textview);

        Intent intent = getIntent();
        mVideoId = intent.getStringExtra("videoId");


        VideoAsyncTask task = new VideoAsyncTask();
        task.execute(mVideoId);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        mVideoView = findViewById(R.id.videoview);

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // media name is an external URL
            return Uri.parse(mediaName);
        } else { // media name is a raw resource embedded in the app
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer() {
        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        Uri videoUri = getMedia(mPreviewUrl);
        mVideoView.setVideoURI(videoUri);

        if (mCurrentPosition > 0) {
            mVideoView.seekTo(mCurrentPosition);
        } else {
            //Skipping to 1 shows the first frame of the video.
            mVideoView.seekTo(1);
        }
        mVideoView.start();

        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            mVideoView.seekTo(1);
                        }

                        mVideoView.start();
                    }
                });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(VideoActivity.this, "Playback completed",
                        Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    public static String getPreviewUrl (String videoId) throws IOException, JSONException {
        String jsonResponse = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(PREVIEW_URL+videoId)
                .get()
                .addHeader("x-rapidapi-key", "86ab38246fmshded8bcac8ff0c75p14b81cjsn8feeaa8ce1aa")
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        jsonResponse = response.body().string();
        Log.v(LOG_TAG, "preview Jsonresponse: " + jsonResponse);

        JSONObject videoResponse = new JSONObject(jsonResponse);
        JSONObject resourceObj = videoResponse.getJSONObject("resource");
        JSONArray previewsArray = resourceObj.getJSONArray("previews");
        JSONObject firstPreview = (JSONObject) previewsArray.get(0);
        String previewUrl = firstPreview.getString("playUrl");
        Log.v(LOG_TAG, "Preview Url: " + previewUrl);

        return previewUrl;
    }

    public class VideoAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... previewId) {

           String previewUrl = null;
            try {
                previewUrl = getPreviewUrl(mVideoId);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return previewUrl;
        }

        @Override
        protected void onPostExecute(String previewUrl) {

            Log.v(LOG_TAG, "completed fetching previewUrl: " + previewUrl);

        }


    }
}
