package com.innominds.driverassist.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.fragments.AddDeviceFragment;
import com.innominds.driverassist.fragments.DeviceDetailsFragment;
import com.innominds.driverassist.fragments.HomeFragment;
import com.innominds.driverassist.fragments.MainFragment;
import com.innominds.driverassist.fragments.RegisterFragment;
import com.innominds.driverassist.model.DeviceData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements OnActionListner {
    public static final int SERVERPORT = 3000;
    public static final String TAG = MainActivity.class.getSimpleName();
    //local ip address
    //public static final String SERVER_IP = "192.168.100.80";
    public static final String SERVER_IP = "192.168.43.1";
    ClientThread clientThread;
    Thread thread;
    CustomSurfaceView mCustomSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLoginSelected();
        /*clientThread = new ClientThread(this);
        thread = new Thread(clientThread);
        thread.start();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onLoginSelected() {
        Fragment mFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainContainer,mFragment)
        .commit();
    }

    @Override
    public void onRegisterSelected() {
        Fragment mFragment = new RegisterFragment();
       getSupportFragmentManager().beginTransaction()
       .replace(R.id.MainContainer,mFragment)
       .addToBackStack(null)
        .commit();
    }

    @Override
    public void onRegisterSuccessSelected() {
        Fragment mFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainContainer,mFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddDeviceSelected() {
        Fragment mFragment = new AddDeviceFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainContainer,mFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeviceSelected(String mSSID, DeviceData deviceData) {
        Fragment mFragment = new DeviceDetailsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("SSID",mSSID);
        mBundle.putParcelable("Devicedata", deviceData);
        mFragment.setArguments(mBundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainContainer,mFragment)
                .addToBackStack(null)
                .commit();
    }
    class ClientThread implements Runnable {

        Context context;
        MainActivity mainActivity;
        private Socket socket;
        private BufferedReader input;

        public ClientThread(Context context) {
            this.context=context;
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
                    /*mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCustomSurfaceView = new CustomSurfaceView(context,pitch,roll);
                        }
                    });*/

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
                Toast.makeText(MainActivity.this,""+message,Toast.LENGTH_SHORT).show();
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
    }

}
