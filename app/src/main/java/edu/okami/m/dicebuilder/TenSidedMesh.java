package edu.okami.m.dicebuilder;

public class TenSidedMesh extends DiceMesh {

    public TenSidedMesh(float radius) {

        double rads = (Math.PI) / 5;

        float y = radius / 20;

        float x1 = radius;
        float z1 = 0;

        float x2 = (float) ((x1 * Math.cos(rads)) - (z1 * Math.sin(rads)));
        float z2 = (float) ((z1 * Math.cos(rads)) + (x1 * Math.sin(rads)));

        float x3 = (float) ((x1 * Math.cos(2 * rads)) - (z1 * Math.sin(2 * rads)));
        float z3 = (float) ((z1 * Math.cos(2 * rads)) + (x1 * Math.sin(2 * rads)));

        float x4 = (float) ((x1 * Math.cos(3 * rads)) - (z1 * Math.sin(3 * rads)));
        float z4 = (float) ((z1 * Math.cos(3 * rads)) + (x1 * Math.sin(3 * rads)));

        float x5 = (float) ((x1 * Math.cos(4 * rads)) - (z1 * Math.sin(4 * rads)));
        float z5 = (float) ((z1 * Math.cos(4 * rads)) + (x1 * Math.sin(4 * rads)));

        float x6 = (float) ((x1 * Math.cos(5 * rads)) - (z1 * Math.sin(5 * rads)));
        float z6 = (float) ((z1 * Math.cos(5 * rads)) + (x1 * Math.sin(5 * rads)));

        float x7 = (float) ((x1 * Math.cos(6 * rads)) - (z1 * Math.sin(6 * rads)));
        float z7 = (float) ((z1 * Math.cos(6 * rads)) + (x1 * Math.sin(6 * rads)));

        float x8 = (float) ((x1 * Math.cos(7 * rads)) - (z1 * Math.sin(7 * rads)));
        float z8 = (float) ((z1 * Math.cos(7 * rads)) + (x1 * Math.sin(7 * rads)));

        float x9 = (float) ((x1 * Math.cos(8 * rads)) - (z1 * Math.sin(8 * rads)));
        float z9 = (float) ((z1 * Math.cos(8 * rads)) + (x1 * Math.sin(8 * rads)));

        float x10 = (float) ((x1 * Math.cos(9 * rads)) - (z1 * Math.sin(9 * rads)));
        float z10 = (float) ((z1 * Math.cos(9 * rads)) + (x1 * Math.sin(9 * rads)));

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

}
