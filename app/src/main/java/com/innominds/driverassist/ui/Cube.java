package com.innominds.driverassist.ui;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cube {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private int numFaces = 6;

   /* private float[][] colors = {  // Colors of the 6 faces
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            {1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };*/

    private float[][] colorGreen = {  // Colors of the 6 faces
            {0.0f, 1.0f, 0.0f, 1.0f},  // 0. orange
            {0.0f, 1.0f, 0.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 1.0f, 0.0f, 1.0f},  // 3. blue
            {0.0f, 1.0f, 0.0f, 1.0f},  // 4. red
            {0.0f, 1.0f, 0.0f, 1.0f}  // 5. yellow
    };
    private float[][] colorRed = {  // Colors of the 6 faces
            {1.0f, 0.0f, 0.0f, 1.0f},   // 0. orange
            {1.0f, 0.0f, 0.0f, 1.0f},   // 1. violet
            {1.0f, 0.0f, 0.0f, 1.0f},   // 2. green
            {1.0f, 0.0f, 0.0f, 1.0f},   // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 0.0f, 0.0f, 1.0f}  // 5. yellow
    };

    private float[] vertices = {  // Vertices of the 6 faces
            // FRONT
            -1.5f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            -1.5f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            // BACK
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.5f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            -1.5f,  1.0f, -1.0f,  // 5. left-top-back
            // LEFT
            -1.5f, -1.0f, -1.0f,  // 4. left-bottom-back
            -1.5f, -1.0f,  1.0f,  // 0. left-bottom-front
            -1.5f,  1.0f, -1.0f,  // 5. left-top-back
            -1.5f,  1.0f,  1.0f,  // 2. left-top-front
            // RIGHT
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            // TOP
            -1.5f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            -1.5f,  1.0f, -1.0f,  // 5. left-top-back
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            // BOTTOM
            -1.5f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.5f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f   // 1. right-bottom-front
    };


    /**
     * The Cube constructor.
     *
     * Initiate the buffers.
     */
    public Cube() {
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    /**
     * The object own drawing function.
     * Called from the renderer to redraw this instance
     * with possible changes in values.
     *
     * @param gl - The GL Context
     */
    public void draw(GL10 gl, float yrot, float xrot)
    {
       /* gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Render all the faces
        for (int face = 0; face < numFaces; face++) {
            // Set the color for each of the faces
            gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);*/



        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);


        /*if (( -6.0f<xrot && xrot<5.0f) && (-2.45f<yrot && yrot<8.0f) ) //lies on table
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorGreen[face][0], colorGreen[face][1], colorGreen[face][2], colorGreen[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }
           *//* Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                mVibrator.vibrate(50);*//*

        }
        else if ( (-1.0f<xrot && xrot<3.5f) && (0.0f<yrot && yrot<30.0f))//tilt left
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (0.0f<xrot && xrot<3.0f) && (-30.0f<yrot && yrot<-1.0f))//tilt right
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (0.0f<xrot && xrot<20.0f) && (-3.0f<yrot && yrot<3.0f))//tilt down
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (-15.0f<xrot && xrot<0.0f) && (-2.0f<yrot && yrot<2.0f))//tilt up
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }*/

        if ((xrot==0.0 && yrot==0.0)|| ( -0.0f<xrot && xrot<7.0f) && (-180f<yrot && yrot<-175.0f) ) //lies on table
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorGreen[face][0], colorGreen[face][1], colorGreen[face][2], colorGreen[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }
            /*Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                mVibrator.vibrate(50);*/

        }
        else if ( (5.1f<xrot && xrot<70f) && (-180f<yrot && yrot<-175.0f))//tilt left
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (-40f<xrot && xrot<0.0f) && (-180f<yrot && yrot<-175.0f))//tilt right
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (0.0f<xrot && xrot<20.0f) && ( yrot<-140.0f))//tilt up
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }

        }
        else if ( (0.0f<xrot && xrot<7.0f) && (160f<yrot && yrot<180f))//tilt down
        {
            for (int face = 0; face < numFaces; face++) {
                // Set the color for each of the faces
                gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                // Draw the primitive from the vertex-array directly
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
            }
        }

         /*else {
             for (int face = 0; face < numFaces; face++) {
                 // Set the color for each of the faces
                 gl.glColor4f(colorRed[face][0], colorRed[face][1], colorRed[face][2], colorRed[face][3]);
                 // Draw the primitive from the vertex-array directly
                 gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
             }
         }*/

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);


    }
}

