package edu.okami.m.dicebuilder;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

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

    public void draw(GL10 gl) {

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
        gl.glRotatef(rotateX, 1, 0, 0);
        gl.glRotatef(rotateY, 0, 1, 0);
        gl.glRotatef(rotateZ, 0, 0, 1);

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

        //gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        //gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
        mBitmap.recycle();

    }

}
