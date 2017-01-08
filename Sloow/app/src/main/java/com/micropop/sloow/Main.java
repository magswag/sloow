package com.micropop.sloow;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static android.R.attr.data;

public class Main extends AppCompatActivity {
    public VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mVideoView = (VideoView) findViewById(R.id.VideoV);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getVideoe();
            }

        });}



            // save your video to SD card
            protected void saveVideo(final Uri uriVideo) {

                // click the video to save it
                mVideoView.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {

                        boolean success = false;

                        // make the directory
                        File vidDir = new File(android.os.Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_MOVIES) + File.separator + "Saved iCute Videos");
                        vidDir.mkdirs();
                        // create unique identifier
                        Random generator = new Random();
                        int n = 100;
                        n = generator.nextInt(n);
                        // create file name
                        String videoName = "Video_" + n + ".mp4";
                        File fileVideo = new File(vidDir.getAbsolutePath(), videoName);

                        try {
                            fileVideo.createNewFile();
                            success = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (success) {
                            Toast.makeText(getApplicationContext(), "Video saved!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error during video saving", Toast.LENGTH_LONG).show();
                        }

                        return true;
                    }
                });
            }

            public void getVideoe() {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                startActivityForResult(intent, 1);
            }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            try
            {
                String path = data.getData().toString();
                mVideoView.setVideoPath(path);
                mVideoView.requestFocus();
                mVideoView.start();

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }


            }
}

