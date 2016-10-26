package com.example.wenchao.myapplication;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wenchao.myapplication.views.MyVideoView;

import java.io.IOException;


/**
 * Created on 2016/9/14.
 *
 * @author wenchao
 * @since 1.0
 */
public class VideoPlayActivity extends Activity implements SurfaceHolder.Callback {

    private MyVideoView movieRV;
    private Button startBtn;
    private Button stopBtn;

    private Button playBtn;
    private Button pauseBtn;

    private SurfaceView playView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videorecorder2);
        initViews();

//        init();

    }

    private void initViews() {
        playView = (SurfaceView) findViewById(R.id.sv_play_activity);
        surfaceHolder = playView.getHolder();
        surfaceHolder.addCallback(this);
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(0.0f, 0.0f);
        mediaPlayer.setDisplay(surfaceHolder);
        //设置显示视频显示在SurfaceView上
        try {
            mediaPlayer.setDataSource("/sdcard/love1.mp4");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("complete!!!");
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
