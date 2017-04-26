package edu.okami.m.dicebuilder;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SixSidedMesh extends DiceMesh {

    private float width;

    public SixSidedMesh(float width) {

        this.width = width;

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 1536.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 1536.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 1536.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 1536.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 1536.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 1536.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 1536.0f;
        float faceFiveLeftBound = (256.0f * 4.0f + 1.0f) / 1536.0f;
        float faceFiveRightBound = (256.0f * 5.0f) / 1536.0f;
        float faceSixLeftBound = (256.0f * 5.0f + 1.0f) / 1536.0f;
        float faceSixRightBound = (256.0f * 6.0f) / 1536.0f;

        float vertices[] = {

                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,

                1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,

                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,

                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,

                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,

                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
        };

        for (int i=0; i<vertices.length; i++) {
            vertices[i] = vertices[i] * (width * 0.5f);
        }

        float textureCoordinates[] = {

                faceOneLeftBound, 1.0f,
                faceOneRightBound, 1.0f,
                faceOneLeftBound, 0.0f,
                faceOneRightBound, 0.0f,

                faceTwoLeftBound, 1.0f,
                faceTwoRightBound, 1.0f,
                faceTwoLeftBound, 0.0f,
                faceTwoRightBound, 0.0f,

                faceThreeLeftBound, 1.0f,
                faceThreeRightBound, 1.0f,
                faceThreeLeftBound, 0.0f,
                faceThreeRightBound, 0.0f,

                faceFourLeftBound, 1.0f,
                faceFourRightBound, 1.0f,
                faceFourLeftBound, 0.0f,
                faceFourRightBound, 0.0f,

                faceFiveLeftBound, 1.0f,
                faceFiveRightBound, 1.0f,
                faceFiveLeftBound, 0.0f,
                faceFiveRightBound, 0.0f,

                faceSixLeftBound, 1.0f,
                faceSixRightBound, 1.0f,
                faceSixLeftBound, 0.0f,
                faceSixRightBound, 0.0f,

        };

        short indices[] = {
                0,1,3,
                0,3,2,
                4,5,7,
                4,7,6,
                8,9,11,
                8,11,10,
                12,13,15,
                12,15,14,
                16,17,19,
                16,19,18,
                20,21,23,
                20,23,22
        };

        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }

    @Override
    public float getRadius() {
        //return width * (float)(Math.sqrt(3)) / 2.0f;
        return width / 2.0f;
    }

    @Override
    public float[] getFlatteningEuelerAngles () {

        float[] flatteningEulerAngles = new float[3];
        float[][] ro = {

                {0, 0, 1}, {0, -1, 0}, {1, 0, 0},
                {-1, 0, 0}, {0, 1, 0}, {0, 0, -1}

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
                {dot(Ro2w[0], ro[5]), dot(Ro2w[1], ro[5]), dot(Ro2w[2], ro[5])}

        };

        float[] down = {0, 0, -1};

        float[] signedAngles = {

                (float) (Math.acos(dot(nw[0], down))),
                (float) (Math.acos(dot(nw[1], down))),
                (float) (Math.acos(dot(nw[2], down))),
                (float) (Math.acos(dot(nw[3], down))),
                (float) (Math.acos(dot(nw[4], down))),
                (float) (Math.acos(dot(nw[5], down)))

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

}
