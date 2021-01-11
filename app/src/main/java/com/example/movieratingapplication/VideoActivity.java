package com.example.movieratingapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private static final String VIDEO_SAMPLE =
            //"https://imdb8.p.rapidapi.com/title/get-videos?tconst="
            //"https://developers.google.com/training/images/tacoma_narrows.mp4";
            "https://imdb-video.media-imdb.com/vi3240214809/1434659607842-pgv4ql-1607305868042.mp4?Expires=1610298386&Signature=RM6QEJ3jagk9al7EOERT48nfLt3t~DXFsI72zUsfRzSV~cBQ1hyoiX~6dqMi6JbLc7Nz7C1lWCkzdmwNwcQJIK1HwJEZJDY4ERSS~nG~u96qNua5CCbLgb2l1U07KvLDF~umtpjZqo-2OYvGpp-jqKuiZSamYGHgNr-FslVs4iwCoQOqxsSLdVAXH2Cr0jYIlskH88AB54oYRRBfpaDhkM~n~sYuXupKuHPzhMTvTgrnXhq6fT07An6HSt5OiWmd8lmGxzv~6cWeRv9tE~b9zDQJHh31-oolgK-~lv1bxuuyAD7kNSEWyiomEMFL~dehMwYx0lYddcW6blPukaNx4g__&Key-Pair-Id=APKAIFLZBVQZ24NQH3KA";
    private VideoView mVideoView;
    private int mCurrentPosition = 0; //playback position is recorded in milliseconds from 0.
    private static final String PLAYBACK_TIME = "play_time";
    private TextView mBufferingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mBufferingTextView = findViewById(R.id.buffering_textview);

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

        Uri videoUri = getMedia(VIDEO_SAMPLE);
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
}
