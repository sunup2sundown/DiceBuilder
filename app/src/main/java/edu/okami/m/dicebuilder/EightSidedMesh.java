package edu.okami.m.dicebuilder;

import android.util.Log;

public class EightSidedMesh extends DiceMesh {

    private float width, height, halfWidth, halfHeight;

    public EightSidedMesh(float width) {

        this.halfHeight = (float) Math.sqrt((width * width) - ((width/2) * (width/2)));
        this.halfWidth = width / 2;

        this.width = width;
        this.height = 2 * halfHeight;

        Log.d("Eight Sided Dimensions", "Half-Height = " + Float.toString(halfHeight) +
                ", Half-Width = " + Float.toString(halfWidth));

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 2048.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 2048.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 2048.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 2048.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 2048.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 2048.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 2048.0f;
        float faceFiveLeftBound = (256.0f * 4.0f + 1.0f) / 2048.0f;
        float faceFiveRightBound = (256.0f * 5.0f) / 2048.0f;
        float faceSixLeftBound = (256.0f * 5.0f + 1.0f) / 2048.0f;
        float faceSixRightBound = (256.0f * 6.0f) / 2048.0f;
        float faceSevenLeftBound = (256.0f * 6.0f + 1.0f) / 2048.0f;
        float faceSevenRightBound = (256.0f * 7.0f) / 2048.0f;
        float faceEightLeftBound = (256.0f * 7.0f + 1.0f) / 2048.0f;
        float faceEightRightBound = (256.0f * 8.0f) / 2048.0f;
        float faceBoundHalfModifier = (128.0f / 2048.0f);


        float vertices[] =
                {
                        -halfWidth, 0.0f, halfWidth,
                        halfWidth, 0.0f, halfWidth,
                        0.0f, halfHeight, 0.0f,

                        -halfWidth, 0.0f, halfWidth,
                        -halfWidth, 0.0f, -halfWidth,
                        0.0f, -halfHeight, 0.0f,

                        -halfWidth, 0.0f, -halfWidth,
                        -halfWidth, 0.0f, halfWidth,
                        0.0f, halfHeight, 0.0f,

                        halfWidth, 0.0f, halfWidth,
                        -halfWidth, 0.0f, halfWidth,
                        0.0f, -halfHeight, 0.0f,

                        halfWidth, 0.0f, -halfWidth,
                        -halfWidth, 0.0f, -halfWidth,
                        0.0f, halfHeight, 0.0f,

                        halfWidth, 0.0f, -halfWidth,
                        halfWidth, 0.0f, halfWidth,
                        0.0f, -halfHeight, 0.0f,

                        halfWidth, 0.0f, halfWidth,
                        halfWidth, 0.0f, -halfWidth,
                        0.0f, halfHeight, 0.0f,

                        -halfWidth, 0.0f, -halfWidth,
                        halfWidth, 0.0f, -halfWidth,
                        0.0f, -halfHeight, 0.0f
                };

        short indices[] =
                {
                        0, 1, 2, //1
                        3, 4, 5, //2
                        6, 7, 8, //3
                        9, 10, 11, //4
                        12, 13, 14, //5
                        15, 16, 17, //6
                        18, 19, 20, //7
                        21, 22, 23 //8
                };

        float textureCoordinates[] =
                {
                        faceOneLeftBound, 1.0f,
                        faceOneRightBound, 1.0f,
                        (faceOneLeftBound + faceBoundHalfModifier), 0.0f,

                        faceTwoLeftBound, 1.0f,
                        faceTwoRightBound, 1.0f,
                        (faceTwoLeftBound + faceBoundHalfModifier), 0.0f,

                        faceThreeLeftBound, 1.0f,
                        faceThreeRightBound, 1.0f,
                        (faceThreeLeftBound + faceBoundHalfModifier), 0.0f,

                        faceFourLeftBound, 1.0f,
                        faceFourRightBound, 1.0f,
                        (faceFourLeftBound + faceBoundHalfModifier), 0.0f,

                        faceFiveLeftBound, 1.0f,
                        faceFiveRightBound, 1.0f,
                        (faceFiveLeftBound + faceBoundHalfModifier), 0.0f,

                        faceSixLeftBound, 1.0f,
                        faceSixRightBound, 1.0f,
                        (faceSixLeftBound + faceBoundHalfModifier), 0.0f,

                        faceSevenLeftBound, 1.0f,
                        faceSevenRightBound, 1.0f,
                        (faceSevenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceEightLeftBound, 1.0f,
                        faceEightRightBound, 1.0f,
                        (faceEightLeftBound + faceBoundHalfModifier), 0.0f
                };

        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }

    @Override
    public float getRadius() {
        return ((halfHeight + halfWidth) / 2);
    }

    @Override
    public float[] getFlatteningEuelerAngles () {

        float[] flatteningEulerAngles = new float[3];
        float oddRoot = (float) Math.sqrt(21/108);
        float root3o6odd = (float) ((Math.sqrt(3) / 6) / oddRoot);
        float oneo3odd = (1 / (3 * oddRoot));
        float[][] ro = {

                {0, root3o6odd, oneo3odd}, {-oneo3odd, -root3o6odd, 0},
                {-oneo3odd, root3o6odd, 0}, {0, -root3o6odd, oneo3odd},
                {0, root3o6odd, -oneo3odd}, {oneo3odd, -root3o6odd, 0},
                {oneo3odd, root3o6odd, 0}, {0, -root3o6odd, -oneo3odd}

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
                {dot(Ro2w[0], ro[7]), dot(Ro2w[1], ro[7]), dot(Ro2w[2], ro[7])}

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
                (float) (Math.acos(dot(nw[7], down)))

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
