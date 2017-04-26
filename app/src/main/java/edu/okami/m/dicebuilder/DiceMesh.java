package edu.okami.m.dicebuilder;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.renderscript.Matrix4f;
import android.util.Log;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class DiceMesh {

    private int numberOfIndices = -1;
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private boolean mShouldLoadTexture = false;

    public float translateX = 0;
    public float translateY = 0;
    public float translateZ = 0;

    public float rotateX = 0;
    public float rotateY = 0;
    public float rotateZ = 0;

    public float theta = 0;
    public float rotateVelocityX = 0;
    public float rotateVelocityY = 0;

    protected float[] matrixArray = new float[16];

    //private float changingTheta = 0;

    public void draw(GL10 gl) {

        GL11 gl11 = (GL11) gl;

        // Counter-clockwise winding
        gl.glFrontFace(GL10.GL_CCW);
        // Enable non-rendering of faces
        gl.glEnable(GL10.GL_CULL_FACE);
        // Choose faces not facing screen to cull
        gl.glCullFace(GL10.GL_BACK);

        if (mShouldLoadTexture) {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
        }
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            // Enable the texture state
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            // Point to our buffers
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        }

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glTranslatef(translateX, translateY, translateZ);
        gl.glRotatef(theta, -rotateVelocityY, rotateVelocityX, 0);

        //Order needed to correspond with Eueler Angle function
        gl.glRotatef(rotateY, 0, 1, 0);
        gl.glRotatef(rotateZ, 0, 0, 1);
        gl.glRotatef(rotateX, 1, 0, 0);

        gl11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, matrixArray, 0);

        /**
         //Test Area
        float[] testVelocity = {3.0f, 5.0f, 0.0f};
        changingTheta++;
        gl.glRotatef(changingTheta, -testVelocity[1], testVelocity[0], 1);

        if (changingTheta < 20) {Log.d("Print the array", Arrays.toString(array));}

        //Test Area
         **/

        gl.glDrawElements(GL10.GL_TRIANGLES, numberOfIndices, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_TEXTURE_2D);

        gl.glLoadIdentity();

    }

    protected void setVertices(float[] vertices) {

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

    }

    protected void setIndices(short[] indices) {

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
        numberOfIndices = indices.length;

    }

    private FloatBuffer mTextureBuffer;

    protected void setTextureCoordinates(float[] textureCoordinates) {

        ByteBuffer byteBuff = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
        byteBuff.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuff.asFloatBuffer();
        mTextureBuffer.put(textureCoordinates);
        mTextureBuffer.position(0);

    }

    private int mTextureId = -1;
    private Bitmap mBitmap;

    public void loadBitmap(Bitmap bitmap) {

        this.mBitmap = bitmap;
        mShouldLoadTexture = true;

    }

    private void loadGLTexture(GL10 gl) {

        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
        mBitmap.recycle();

    }

    public float getRadius() {
        return 0.0f;
    }

    public float[] getEulerAngles() {

        //Adapted from algorithm at http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToEuler/

        float[] eulerAngles = new float[3];

        if (matrixArray[1] > 0.998) { //singularity at north pole

            eulerAngles[0] = (float) Math.atan2(matrixArray[8], matrixArray[10]);
            eulerAngles[1] = (float) (Math.PI / 2);
            eulerAngles[2] = 0;


        }
        else if (matrixArray[1] < -0.998) { // singularity at south pole

            eulerAngles[0] = (float) Math.atan2(matrixArray[8], matrixArray[10]);
            eulerAngles[1] = (float) - (Math.PI / 2);
            eulerAngles[2] = 0;

        }
        else {

            eulerAngles[0] = (float) Math.atan2(-matrixArray[2], matrixArray[0]);
            eulerAngles[1] = (float) Math.asin(matrixArray[1]);
            eulerAngles[2] = (float) Math.atan2(-matrixArray[9], matrixArray[5]);

        }

        eulerAngles[0] = (float) Math.rint(Math.toDegrees(eulerAngles[0]));
        eulerAngles[1] = (float) Math.rint(Math.toDegrees(eulerAngles[1]));
        eulerAngles[2] = (float) Math.rint(Math.toDegrees(eulerAngles[2]));

        //This triumvirate is experimental
        if (eulerAngles[0] < 0.0f) {eulerAngles[0] += 360.0f;}
        if (eulerAngles[1] < 0.0f) {eulerAngles[1] += 360.0f;}
        if (eulerAngles[2] < 0.0f) {eulerAngles[2] += 360.0f;}

        if (eulerAngles[0] == -0.0f) {eulerAngles[0] = 0.0f;}
        if (eulerAngles[1] == -0.0f) {eulerAngles[1] = 0.0f;}
        if (eulerAngles[2] == -0.0f) {eulerAngles[2] = 0.0f;}

        return eulerAngles;

    }

    public float[] getFlatteningEuelerAngles () {

        return null;

    }

    protected float dot(float[] a, float[] b) {

        float dotProduct = 0;

        for (int i = 0; i < a.length; i++) {
            dotProduct += (a[i] * b[i]);
        }

        return dotProduct;

    }

    protected float[] cross(float[] a, float[] b) {

        float crossProduct[] = new float[3];

        crossProduct[0] = a[1] * b[2] - b[1] * a[2];
        crossProduct[1] = b[0] * a[2] - a[0] * b[2];
        crossProduct[2] = a[0] * b[1] - b[0] * a[1];

        /**
        if ((crossProduct[0] == 0) && (crossProduct[1] == 0) && (crossProduct[2]== 0)) {
            return a;
        }
        else {
            return crossProduct;
        }
         **/

        return crossProduct;

    }

    protected float[] normalize(float[] vector) {

        float magnitude = (float) (Math.sqrt(

                (vector[0] * vector[0]) + (vector[1] * vector[1]) + (vector[2] * vector[2])

        ));

        float[] normalizedVector = new float[3];
        normalizedVector[0] = vector[0] / magnitude;
        normalizedVector[1] = vector[1] / magnitude;
        normalizedVector[2] = vector[2] / magnitude;

        return normalizedVector;

    }

}
