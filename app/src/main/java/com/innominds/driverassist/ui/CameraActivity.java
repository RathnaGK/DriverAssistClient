package com.innominds.driverassist.ui;

import android.app.ProgressDialog;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.presenters.CameraActivityPresenter;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.CameraActivityView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener,CameraActivityView {

    @BindView(R.id.mVideoview)
    TextureView mVideoview;

   /* @BindView(R.id.mVideoview)
    SurfaceView mVideoview;*/

    @BindView(R.id.spRotation)
    Spinner spRotation;
    public static MediaPlayer mMediaPlayer;
    String TAG = "Media Player";
    private  CameraActivityPresenter mCameraActivityPresenter;
    ArrayAdapter<String> adapter;
    String url;
    int check=0;
    String ipaddress;
    Handler mHandler;
    ProgressDialog mProgressDialog;
    ArrayList<String> mArrayList;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.actvity_camera);
        ipaddress = Utils.getApIpAddr(this);
        //url = this.getResources().getString(R.string.rtsp_url, ipaddress);
        url = "rtsp://192.168.100.80:1935/live/assist";
        ButterKnife.bind(this);
        mCameraActivityPresenter = new CameraActivityPresenter(getApplicationContext(),this);
        mHandler = new Handler();

        mArrayList = new ArrayList<>();
        mArrayList.add("Rotation");
        mArrayList.add("0");
        mArrayList.add("90");
        mArrayList.add("180");
        mArrayList.add("270");

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,mArrayList);
        spRotation.setAdapter(adapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mProgressDialog.setCancelable(false);

        mProgressDialog.setMessage("Please wait..");
        mProgressDialog.show();

       //mVideoview.setRotation(90f);
        spRotation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, final int position, long l) {
                if (++check>1)
                {
                    mCameraActivityPresenter.sendRotation(adapterView.getItemAtPosition(position).toString());

                    mProgressDialog.setMessage("please wait");
                    mProgressDialog.show();
                    if (mMediaPlayer!=null)
                    {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mVideoview.isAvailable())
                                mVideoview.getSurfaceTextureListener().onSurfaceTextureAvailable(mVideoview.getSurfaceTexture(),mVideoview.getWidth(),mVideoview.getHeight());

                          mVideoview.setRotation(Float.parseFloat(adapterView.getItemAtPosition(position).toString()));
                            mProgressDialog.dismiss();
                        }
                    },5000);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mVideoview.setSurfaceTextureListener(new TextureView.SurfaceTextureListener()
        {

            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

                Surface s = new Surface(surfaceTexture);
                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(url);
                    mMediaPlayer.setSurface(s);
                    mMediaPlayer.setOnVideoSizeChangedListener(CameraActivity.this);
                    mMediaPlayer.setOnBufferingUpdateListener(CameraActivity.this);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(CameraActivity.this);
                }  catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int w, int h) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });

    }

    @Override
    public void onError(int resId) {
        Utils.showError(this.getWindow().getDecorView(), getApplicationContext(), resId);
    }

    @Override
    public void onSuccess(String resID)
    {
        Log.d(TAG, "onSuccess:     resTD");
    }



    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        mVideoview.removeCallbacks(null );
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("start","start mediaplayer");
        mProgressDialog.dismiss();
        mediaPlayer.start();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed()
    {
                mCameraActivityPresenter.sendRotation("90");
                if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
                {
                    mMediaPlayer.stop();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMediaPlayer.release();
                            mMediaPlayer = null;
                        }
                    },5000);

                }
             // mCameraActivityPresenter.sendStopCommand("stop");
                finish();

    }

}


