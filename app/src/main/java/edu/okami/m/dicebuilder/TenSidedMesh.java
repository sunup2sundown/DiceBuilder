package edu.okami.m.dicebuilder;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class TenSidedMesh extends DiceMesh {

    private float radius, y;
    private float x1, x2, x3, x4, x5, x6, x7, x8, x9, x10;
    private float z1, z2, z3, z4, z5, z6, z7, z8, z9, z10;

    public TenSidedMesh(float radius) {

        this.radius = radius;

        double rads = (Math.PI) / 5;

        this.y = radius / 20;

        this.x1 = radius;
        this.z1 = 0;

        this.x2 = (float) ((x1 * Math.cos(rads)) - (z1 * Math.sin(rads)));
        this.z2 = (float) ((z1 * Math.cos(rads)) + (x1 * Math.sin(rads)));

        this.x3 = (float) ((x1 * Math.cos(2 * rads)) - (z1 * Math.sin(2 * rads)));
        this.z3 = (float) ((z1 * Math.cos(2 * rads)) + (x1 * Math.sin(2 * rads)));

        this.x4 = (float) ((x1 * Math.cos(3 * rads)) - (z1 * Math.sin(3 * rads)));
        this.z4 = (float) ((z1 * Math.cos(3 * rads)) + (x1 * Math.sin(3 * rads)));

        this.x5 = (float) ((x1 * Math.cos(4 * rads)) - (z1 * Math.sin(4 * rads)));
        this.z5 = (float) ((z1 * Math.cos(4 * rads)) + (x1 * Math.sin(4 * rads)));

        this.x6 = (float) ((x1 * Math.cos(5 * rads)) - (z1 * Math.sin(5 * rads)));
        this.z6 = (float) ((z1 * Math.cos(5 * rads)) + (x1 * Math.sin(5 * rads)));

        this.x7 = (float) ((x1 * Math.cos(6 * rads)) - (z1 * Math.sin(6 * rads)));
        this.z7 = (float) ((z1 * Math.cos(6 * rads)) + (x1 * Math.sin(6 * rads)));

        this.x8 = (float) ((x1 * Math.cos(7 * rads)) - (z1 * Math.sin(7 * rads)));
        this.z8 = (float) ((z1 * Math.cos(7 * rads)) + (x1 * Math.sin(7 * rads)));

        this.x9 = (float) ((x1 * Math.cos(8 * rads)) - (z1 * Math.sin(8 * rads)));
        this.z9 = (float) ((z1 * Math.cos(8 * rads)) + (x1 * Math.sin(8 * rads)));

        this.x10 = (float) ((x1 * Math.cos(9 * rads)) - (z1 * Math.sin(9 * rads)));
        this.z10 = (float) ((z1 * Math.cos(9 * rads)) + (x1 * Math.sin(9 * rads)));

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 2560.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 2560.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 2560.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 2560.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 2560.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 2560.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 2560.0f;
        float faceFiveLeftBound = (256.0f * 4.0f + 1.0f) / 2560.0f;
        float faceFiveRightBound = (256.0f * 5.0f) / 2560.0f;
        float faceSixLeftBound = (256.0f * 5.0f + 1.0f) / 2560.0f;
        float faceSixRightBound = (256.0f * 6.0f) / 2560.0f;
        float faceSevenLeftBound = (256.0f * 6.0f + 1.0f) / 2560.0f;
        float faceSevenRightBound = (256.0f * 7.0f) / 2560.0f;
        float faceEightLeftBound = (256.0f * 7.0f + 1.0f) / 2560.0f;
        float faceEightRightBound = (256.0f * 8.0f) / 2560.0f;
        float faceNineLeftBound = (256.0f * 8.0f + 1.0f) / 2560.0f;
        float faceNineRightBound = (256.0f * 9.0f) / 2560.0f;
        float faceZeroLeftBound = (256.0f * 9.0f + 1.0f) / 2560.0f;
        float faceZeroRightBound = (256.0f * 10.0f) / 2560.0f;
        float faceBoundHalfModifier = (128.0f / 2560.0f);

        float vertices[] =
                {
                        x5, -y, z5, //1 Face
                        x4, y, z4,
                        0.0f, -radius, 0.0f,

                        x4, y, z4,
                        x3, -y, z3,
                        0.0f, -radius, 0.0f,

                        x6, y, z6, //2 Face
                        x7, -y, z7,
                        0.0f, radius, 0.0f,

                        x7, -y, z7,
                        x8, y, z8,
                        0.0f, radius, 0.0f,

                        x1, -y, z1, //3 Face
                        x10, y, z10,
                        0.0f, -radius, 0.0f,

                        x10, y, z10,
                        x9, -y, z9,
                        0.0f, -radius, 0.0f,

                        x2, y, z2, //4 Face
                        x3, -y, z3,
                        0.0f, radius, 0.0f,

                        x3, -y, z3,
                        x4, y, z4,
                        0.0f, radius, 0.0f,

                        x9, -y, z9, //5 Face
                        x8, y, z8,
                        0.0f, -radius, 0.0f,

                        x8, y, z8,
                        x7, -y, z7,
                        0.0f, -radius, 0.0f,

                        x4, y, z4, //6 Face
                        x5, -y, z5,
                        0.0f, radius, 0.0f,

                        x5, -y, z5,
                        x6, y, z6,
                        0.0f, radius, 0.0f,

                        x3, -y, z3, //7 Face
                        x2, y, z2,
                        0.0f, -radius, 0.0f,

                        x2, y, z2,
                        x1, -y, z1,
                        0.0f, -radius, 0.0f,

                        x8, y, z8, //8 Face
                        x9, -y, z9,
                        0.0f, radius, 0.0f,

                        x9, -y, z9,
                        x10, y, z10,
                        0.0f, radius, 0.0f,

                        x7, -y, z7, //9 Face
                        x6, y, z6,
                        0.0f, -radius, 0.0f,

                        x6, y, z6,
                        x5, -y, z5,
                        0.0f, -radius, 0.0f,

                        x10, y, z10, //0 Face
                        x1, -y, z1,
                        0.0f, radius, 0.0f,

                        x1, -y, z1,
                        x2, y, z2,
                        0.0f, radius, 0.0f
                };

        short indices[] =
                {
                        0, 1, 2,
                        3, 4, 5,

                        6, 7, 8,
                        9, 10, 11,

                        12, 13, 14,
                        15, 16, 17,

                        18, 19, 20,
                        21, 22, 23,

                        24, 25, 26,
                        27, 28, 29,

                        30, 31, 32,
                        33, 34, 35,

                        36, 37, 38,
                        39, 40, 41,

                        42, 43, 44,
                        45, 46, 47,

                        48, 49, 50,
                        51, 52, 53,

                        54, 55, 56,
                        57, 58, 59
                };

        float textureCoordinates[] =
                {
                        faceOneLeftBound, 1.0f - y,
                        faceOneLeftBound + faceBoundHalfModifier, 1.0f,
                        faceOneLeftBound + faceBoundHalfModifier, 0.0f,

                        faceOneLeftBound + faceBoundHalfModifier, 1.0f,
                        faceOneRightBound, 1.0f - y,
                        faceOneLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwoLeftBound, 1.0f - y,
                        faceTwoLeftBound + faceBoundHalfModifier, 1.0f,
                        faceTwoLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwoLeftBound + faceBoundHalfModifier, 1.0f,
                        faceTwoRightBound, 1.0f - y,
                        faceTwoLeftBound + faceBoundHalfModifier, 0.0f,

                        faceThreeLeftBound, 1.0f - y,
                        faceThreeLeftBound + faceBoundHalfModifier, 1.0f,
                        faceThreeLeftBound + faceBoundHalfModifier, 0.0f,

                        faceThreeLeftBound + faceBoundHalfModifier, 1.0f,
                        faceThreeRightBound, 1.0f - y,
                        faceThreeLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFourLeftBound, 1.0f - y,
                        faceFourLeftBound + faceBoundHalfModifier, 1.0f,
                        faceFourLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFourLeftBound + faceBoundHalfModifier, 1.0f,
                        faceFourRightBound, 1.0f - y,
                        faceFourLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFiveLeftBound, 1.0f - y,
                        faceFiveLeftBound + faceBoundHalfModifier, 1.0f,
                        faceFiveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFiveLeftBound + faceBoundHalfModifier, 1.0f,
                        faceFiveRightBound, 1.0f - y,
                        faceFiveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSixLeftBound, 1.0f - y,
                        faceSixLeftBound + faceBoundHalfModifier, 1.0f,
                        faceSixLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSixLeftBound + faceBoundHalfModifier, 1.0f,
                        faceSixRightBound, 1.0f - y,
                        faceSixLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSevenLeftBound, 1.0f - y,
                        faceSevenLeftBound + faceBoundHalfModifier, 1.0f,
                        faceSevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSevenLeftBound + faceBoundHalfModifier, 1.0f,
                        faceSevenRightBound, 1.0f - y,
                        faceSevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceEightLeftBound, 1.0f - y,
                        faceEightLeftBound + faceBoundHalfModifier, 1.0f,
                        faceEightLeftBound + faceBoundHalfModifier, 0.0f,

                        faceEightLeftBound + faceBoundHalfModifier, 1.0f,
                        faceEightRightBound, 1.0f - y,
                        faceEightLeftBound + faceBoundHalfModifier, 0.0f,

                        faceNineLeftBound, 1.0f - y,
                        faceNineLeftBound + faceBoundHalfModifier, 1.0f,
                        faceNineLeftBound + faceBoundHalfModifier, 0.0f,

                        faceNineLeftBound + faceBoundHalfModifier, 1.0f,
                        faceNineRightBound, 1.0f - y,
                        faceNineLeftBound + faceBoundHalfModifier, 0.0f,

                        faceZeroLeftBound, 1.0f - y,
                        faceZeroLeftBound + faceBoundHalfModifier, 1.0f,
                        faceZeroLeftBound + faceBoundHalfModifier, 0.0f,

                        faceZeroLeftBound + faceBoundHalfModifier, 1.0f,
                        faceZeroRightBound, 1.0f - y,
                        faceZeroLeftBound + faceBoundHalfModifier, 0.0f
                };


        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public float[] getFlatteningEuelerAngles () {

        float[] flatteningEulerAngles = new float[3];

        float[][] centroids = {

                {(x4/2), ((y-radius)/2), (z4/2)},
                {(x7/2), ((radius-y)/2), (z7/2)},
                {(x10/2), ((y-radius)/2), (z10/2)},
                {(x3/2), ((radius-y)/2), (z3/2)},
                {(x8/2), ((y-radius)/2), (z8/2)},
                {(x5/2), ((radius-y)/2), (z5/2)},
                {(x2/2), ((y-radius)/2), (z2/2)},
                {(x9/2), ((radius-y)/2), (z9/2)},
                {(x6/2), ((y-radius)/2), (z6/2)},
                {(x1/2), ((radius-y)/2), (z1/2)}

        };

        float[][] ro = {

                normalize(centroids[0]),
                normalize(centroids[1]),
                normalize(centroids[2]),
                normalize(centroids[3]),
                normalize(centroids[4]),
                normalize(centroids[5]),
                normalize(centroids[6]),
                normalize(centroids[7]),
                normalize(centroids[8]),
                normalize(centroids[9])

        };

        float[][] Ro2w = {
                {matrixArray[0], matrixArray[4], matrixArray[8]},
                {matrixArray[1], matrixArray[5], matrixArray[9]},
                {matrixArray[2], matrixArray[6], matrixArray[10]}
        };

        float[][] nw = {

                {dot(Ro2w[0], ro[0]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[1]), dot(Ro2w[1], ro[1]), dot(Ro2w[2], ro[1])},
                {dot(Ro2w[0], ro[2]), dot(Ro2w[1], ro[2]), dot(Ro2w[2], ro[2])},
                {dot(Ro2w[0], ro[3]), dot(Ro2w[1], ro[3]), dot(Ro2w[2], ro[3])},
                {dot(Ro2w[0], ro[4]), dot(Ro2w[1], ro[4]), dot(Ro2w[2], ro[4])},
                {dot(Ro2w[0], ro[5]), dot(Ro2w[1], ro[5]), dot(Ro2w[2], ro[5])},
                {dot(Ro2w[0], ro[6]), dot(Ro2w[1], ro[6]), dot(Ro2w[2], ro[6])},
                {dot(Ro2w[0], ro[7]), dot(Ro2w[1], ro[7]), dot(Ro2w[2], ro[7])},
                {dot(Ro2w[0], ro[8]), dot(Ro2w[1], ro[8]), dot(Ro2w[2], ro[8])},
                {dot(Ro2w[0], ro[9]), dot(Ro2w[1], ro[9]), dot(Ro2w[2], ro[9])}

        };

        float[] down = {0, 0, -1};

        float[] signedAngles = {

                (float) (Math.acos(dot(nw[0], down))),
                (float) (Math.acos(dot(nw[1], down))),
                (float) (Math.acos(dot(nw[2], down))),
                (float) (Math.acos(dot(nw[3], down))),
                (float) (Math.acos(dot(nw[4], down))),
                (float) (Math.acos(dot(nw[5], down))),
                (float) (Math.acos(dot(nw[6], down))),
                (float) (Math.acos(dot(nw[7], down))),
                (float) (Math.acos(dot(nw[8], down))),
                (float) (Math.acos(dot(nw[9], down)))

        };

        float closestAngle = Float.valueOf(signedAngles[0]);
        int closestIndex = 0;

        for (int i = 1; i < signedAngles.length; i++) {
            if (signedAngles[i] < closestAngle) {
                closestAngle = Float.valueOf(signedAngles[i]);
                closestIndex = i;
            }
        }

        float[] zaxNewWInO = {-ro[closestIndex][0], -ro[closestIndex][1], -ro[closestIndex][2]};
        float[] yaxOldWInO = Ro2w[1];
        float[] xaxNewWInO = normalize(cross(yaxOldWInO, zaxNewWInO));
        float[] yaxNewWInO = normalize(cross(zaxNewWInO, xaxNewWInO));

        float[][] newRo2w = {xaxNewWInO, yaxNewWInO, zaxNewWInO};

        if (newRo2w[1][0] > 0.998) { //singularity at north pole

            flatteningEulerAngles[0] = (float) Math.atan2(newRo2w[0][2], newRo2w[2][2]);
            flatteningEulerAngles[1] = (float) (Math.PI / 2);
            flatteningEulerAngles[2] = 0;


        }
        else if (newRo2w[1][0] < -0.998) { // singularity at south pole

            flatteningEulerAngles[0] = (float) Math.atan2(newRo2w[0][2], newRo2w[2][2]);
            flatteningEulerAngles[1] = (float) - (Math.PI / 2);
            flatteningEulerAngles[2] = 0;

        }
        else {

            flatteningEulerAngles[0] = (float) Math.atan2(-newRo2w[2][0], newRo2w[0][0]);
            flatteningEulerAngles[1] = (float) Math.asin(newRo2w[1][0]);
            flatteningEulerAngles[2] = (float) Math.atan2(-newRo2w[1][2], newRo2w[1][1]);

        }

        flatteningEulerAngles[0] = (float) Math.rint(Math.toDegrees(flatteningEulerAngles[0]));
        flatteningEulerAngles[1] = (float) Math.rint(Math.toDegrees(flatteningEulerAngles[1]));
        flatteningEulerAngles[2] = (float) Math.rint(Math.toDegrees(flatteningEulerAngles[2]));

        if (flatteningEulerAngles[0] < 0.0f) {flatteningEulerAngles[0] += 360.0f;}
        if (flatteningEulerAngles[1] < 0.0f) {flatteningEulerAngles[1] += 360.0f;}
        if (flatteningEulerAngles[2] < 0.0f) {flatteningEulerAngles[2] += 360.0f;}

        if (flatteningEulerAngles[0] == -0.0f) {flatteningEulerAngles[0] = 0.0f;}
        if (flatteningEulerAngles[1] == -0.0f) {flatteningEulerAngles[1] = 0.0f;}
        if (flatteningEulerAngles[2] == -0.0f) {flatteningEulerAngles[2] = 0.0f;}

        return flatteningEulerAngles;

    }

    @Override
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
        gl.glRotatef(theta, rotateVelocityY, -rotateVelocityX, 0);

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

}
