package com.innominds.driverassist.ui;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.innominds.driverassist.ui.MainActivity.TAG;

public class InstallationActivity extends AppCompatActivity {
    public static final String TAG = InstallationActivity.class.getSimpleName() ;
    private CustomSurfaceView mCustomSurfaceView;
    public static final int SERVERPORT = 3000;
    //public static final String SERVER_IP = "192.168.100.80";
    public static final String SERVER_IP = "192.168.43.1";
    ClientThread clientThread;
    Thread thread;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomSurfaceView = new CustomSurfaceView(this);
        setContentView(mCustomSurfaceView);
        clientThread = new ClientThread(this);
        thread = new Thread(clientThread);
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCustomSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCustomSurfaceView.onResume();
    }

    class ClientThread implements Runnable {

        Context context;
        InstallationActivity mInstallationActivity;
        private Socket socket;
        private BufferedReader input;

        public ClientThread(Context context) {
            this.context=context;
           // this.mInstallationActivity = mInstallationActivity;
        }

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                socket.setSoTimeout(20000);
                while (!Thread.currentThread().isInterrupted()) {

                    Log.i(TAG, "Waiting for message from server...");

                    this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String pitch = input.readLine();
                    final String roll = input.readLine();
                    //Log.d(TAG, "pitch and roll value" + pitch    + roll    );
                    Log.i(TAG, "Message received from the server : " + pitch  + roll);

                    if (null == pitch || "Disconnect".contentEquals(pitch)) {
                        Thread.interrupted();
                        //pitch = "Server Disconnected.";
                        updateMessage(" | Server : " + pitch);
                        break;
                    }

                    // updateMessage(" | Server : " + message);

                    /*Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCustomSurfaceView = new CustomSurfaceView(context,pitch,roll);
                        }
                    });
*/
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            Toast.makeText(InstallationActivity.this,""+pitch+",,,,,"+ roll,Toast.LENGTH_SHORT).show();
                          mCustomSurfaceView = new CustomSurfaceView(context,pitch,roll);
                        }
                    });*/
                    Log.i(TAG, "before send pitcha nd roll " +"pitch " +pitch+"..."  +"roll " +roll);
                    sendRotationData(pitch,roll);
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        void sendMessage(String message) {
            try {
                if (null != socket) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                            true);
                    out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    public void updateMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InstallationActivity.this,""+message,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != clientThread) {
            clientThread.sendMessage("Disconnect");
            clientThread = null;
        }
        if (CameraActivity.mMediaPlayer!=null)
        {
            CameraActivity.mMediaPlayer.stop();
            CameraActivity.mMediaPlayer.release();
        }
    }

    public void sendRotationData(String pitch,String roll)
    {
        Intent mIntent = new Intent("event");
        mIntent.putExtra("pitch", pitch);
        mIntent.putExtra("roll", roll);
        Log.d("InstallActivity", "final roll " + roll+".."+ "final pitch "+ pitch);
        LocalBroadcastManager.getInstance(this).sendBroadcast(mIntent);
    }


}

