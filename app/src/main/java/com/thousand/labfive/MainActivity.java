package com.thousand.labfive;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button start;
    Button stop;
    Button playAudio;
    Button stopPlaying ;

    MediaRecorder    mr;
    Random r;

    public static final int RequestPermissionCode = 154;
    MediaPlayer mp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIdElements();
        setOnClick();
    }

    public void setIdElements(){
        start = (Button) findViewById(R.id.record);
        stop = (Button) findViewById(R.id.stopPlayingRecords);
        playAudio = (Button) findViewById(R.id.play);
        stopPlaying = (Button)findViewById(R.id.stop);

        stop.setEnabled(false);
        playAudio.setEnabled(false);
        stopPlaying.setEnabled(false);

        r = new Random();
    }

    String str = null;
    public void setOnClick(){
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    str =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mr.prepare();
                        mr.start();
                    } catch (IllegalStateException e) {

                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                    start.setEnabled(false);
                    stop.setEnabled(true);


                } else {
                    requestPermission();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mr.stop();
                stop.setEnabled(false);
                playAudio.setEnabled(true);
                start.setEnabled(true);
                stopPlaying.setEnabled(false);

            }
        });

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                stop.setEnabled(false);
                start.setEnabled(false);
                stopPlaying.setEnabled(true);

                mp = new MediaPlayer();
                try {
                    mp.setDataSource(str);
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mp.start();

            }
        });

        stopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setEnabled(false);
                start.setEnabled(true);
                stopPlaying.setEnabled(false);
                playAudio.setEnabled(true);

                if(mp != null){
                    mp.stop();
                    mp.release();
                    MediaRecorderReady();
                }
            }
        });
    }

    public void MediaRecorderReady(){
        mr=new MediaRecorder();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mr.setOutputFile(str);
    }

    String fileName = "ABCDEFGHIJKLMNOP";
    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(fileName.
                    charAt(r.nextInt(fileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int a,
                                           String permissions[], int[] grantResults) {
        switch (a) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {

                    } else {

                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}