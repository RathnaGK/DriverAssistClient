package com.innominds.driverassist.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.innominds.driverassist.ui.MainActivity.TAG;


public class CustomSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer{


    private Cube cube;

    /* Rotation values */
    private float xrot;					//X Rotation
    private float yrot;					//Y Rotation

    /* Rotation speed values */
    private float xspeed;				//X Rotation Speed ( NEW )
    private float yspeed;				//Y Rotation Speed ( NEW )

    private float z = -5.0f;			//Depth Into The Screen ( NEW )
    private float oldX;
    private float oldY;
    private Context context;

    public  float finalPitch; //pitch
    public  float finalRoll; //roll
    public static float azimuth ; //azimuth


    public CustomSurfaceView(Context context)
    {
        super(context);
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, new IntentFilter("event"));
        this.setRenderer(this);
        this.context = context;
        cube = new Cube();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * Here we do our drawing
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finalPitch = Float.parseFloat(intent.getStringExtra("pitch"));
            finalRoll = Float.parseFloat(intent.getStringExtra("roll"));
        }
    };
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();					//Reset The Current Modelview Matrix

        //Drawing
        gl.glTranslatef(0.0f, 0.0f, z);			//Move z units into the screen
        gl.glScalef(0.6f, 0.6f, 0.6f); 			//Scale the Cube to 80 percent, otherwise it would be too large for the screen

        //Rotate around the axis based on the rotation matrix (rotation, x, y, z)
        gl.glRotatef(finalRoll, 1.0f, 0.0f, 0.0f);	//X
        gl.glRotatef(finalPitch, 0.0f, 1.0f, 0.0f);	//Y

        //cube.draw(gl);					//Draw the Cube
        Log.d("CustomView", "final roll " + finalRoll+".."+ "final pitch "+ finalPitch);
        cube.draw(gl,finalRoll,finalPitch);
    }

    /**
     * If the surface changes, reset the view
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(height == 0) { 						//Prevent A Divide By Zero By
            height = 1; 						//Making Height Equal One
        }
        gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
        gl.glLoadIdentity(); 					//Reset The Projection Matrix
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
        gl.glLoadIdentity(); 					//Reset The Modelview Matrix
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);				//Disable dithering ( NEW )
        gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
        gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }
}


