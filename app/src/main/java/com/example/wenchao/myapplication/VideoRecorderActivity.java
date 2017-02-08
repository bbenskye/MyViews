package com.example.wenchao.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created on 2016/9/14.
 *
 * @author wenchao
 * @since 1.0
 */
public class VideoRecorderActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener,
        MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Camera mCamera;
    private MediaRecorder mediarecorder;
    private Button btn_start, btn_pause, btn_stop, btn_ptr;
    private ProgressBar mProgress;
    private Timer mTimer;
    private int mTimeCount;
    private static boolean isstop = false;
    WindowManager.LayoutParams wmParams;
    WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videorecorder);

        initView();
    }

    private void initView() {


//        final Button button = new Button(getApplicationContext());
//        wm = (WindowManager) getApplicationContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        wmParams = new WindowManager.LayoutParams();
//
//        /**
//         * 以下都是WindowManager.LayoutParams的相关属性 具体用途请参考SDK文档
//         */
//        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 这里是关键，你也可以试试2003
////        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 这里是关键，你也可以试试2003
//        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
//        /**
//         * 这里的flags也很关键 代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
//         * 40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
//         */
//        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//        wmParams.width = 5;
//        wmParams.height = 5;
//        wmParams.gravity= Gravity.LEFT|Gravity.TOP;
//        wmParams.x = 0;
//        wmParams.y = 0;
//

//        // 设置悬浮窗的Touch监听
//        button.setOnTouchListener(new View.OnTouchListener() {
//            int lastX, lastY;
//            int paramX, paramY;
//
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = (int) event.getRawX();
//                        lastY = (int) event.getRawY();
//                        paramX = wmParams.x;
//                        paramY = wmParams.y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int dx = (int) event.getRawX() - lastX;
//                        int dy = (int) event.getRawY() - lastY;
//                        wmParams.x = paramX + dx;
//                        wmParams.y = paramY + dy;
//                        // 更新悬浮窗位置
//                        wm.updateViewLayout(button, wmParams);
//                        break;
//                }
//                return true;
//            }
//        });
//        wm.addView(button, wmParams); // 创建View


        surfaceView = (SurfaceView) findViewById(R.id.surface_view_video);
        btn_start = (Button) findViewById(R.id.push_to_recorder);
        btn_stop = (Button) findViewById(R.id.stop_to_recorder);
        btn_pause = (Button) findViewById(R.id.pause_to_recorder);
        btn_ptr = (Button) findViewById(R.id.btn_start);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setMax(10);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_ptr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecord();
//                        Toast.makeText(VideoRecorderActivity.this, "down", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isstop)
                            stop();
//                        Toast.makeText(VideoRecorderActivity.this, "up", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        try {
            initCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.holder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.push_to_recorder:
                startRecord();
                break;
            case R.id.pause_to_recorder:
                break;
            case R.id.stop_to_recorder:
                stop();
                Toast.makeText(VideoRecorderActivity.this, "stop!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void stop() {
        isstop = true;
        mProgress.setProgress(0);
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mediarecorder != null) {
            // 停止录制
            mediarecorder.stop();
            // 释放资源
            mediarecorder.release();
            mediarecorder = null;

            freeCameraResource();
        }

        Intent i = new Intent(this, VideoPlayActivity.class);
        startActivity(i);
    }

    private void startRecord() {
        isstop = false;
        mediarecorder = new MediaRecorder();// 创建mediarecorder对象

        mediarecorder.reset();
        if (mCamera != null)
            mediarecorder.setCamera(mCamera);

        // 设置录制视频源为Camera(相机)
        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        // 设置录制的视频编码mp4
        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediarecorder.setOrientationHint(90);
        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
        mediarecorder.setVideoSize(176, 144);
//                mediarecorder.setVideoSize(240, 180);
        // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
        mediarecorder.setVideoFrameRate(30);
        mediarecorder.setPreviewDisplay(holder.getSurface());
        // 设置视频文件输出的路径
        mediarecorder.setOutputFile("/sdcard/love1.mp4");
        try {
            // 准备录制
            mediarecorder.prepare();
//                    mediarecorder.setOnErrorListener(this);
//                    mediarecorder.setOnInfoListener(this);
            System.out.println("start!!!!!!!!!!");
            // 开始录制
            mediarecorder.start();
            System.out.println("start!!!!!!!!!!");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            System.out.println("illeg!!!!!!!!!!");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(VideoRecorderActivity.this, "start!", Toast.LENGTH_SHORT).show();

        mTimeCount = 0;// 时间计数器重新赋值
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mTimeCount++;
                mProgress.setProgress(mTimeCount);// 设置进度条
                if (mTimeCount == 10) {// 达到指定时间，停止拍摄
                    stop();
//                            if (mOnRecordFinishListener != null)
//                                mOnRecordFinishListener.onRecordFinish();
                }
            }
        }, 0, 1000);

    }

    /**
     * 初始化摄像头
     *
     * @throws IOException
     * @author wen
     * @date 2015-3-16
     */
    private void initCamera() throws IOException {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;

        setCameraParams();
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(holder);
        mCamera.startPreview();
        mCamera.unlock();
    }

    /**
     * 设置摄像头为竖屏
     *
     * @author wen
     * @date 2015-3-16
     */
    private void setCameraParams() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            params.set("orientation", "portrait");
            mCamera.setParameters(params);
        }
    }

    /**
     * 释放摄像头资源
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        System.out.println("error!!!!!!!!!!");
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        System.out.println("onInfo!!!!!!!!!!");
    }
}
