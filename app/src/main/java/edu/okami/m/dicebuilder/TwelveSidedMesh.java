package edu.okami.m.dicebuilder;

import android.util.Log;

public class TwelveSidedMesh extends DiceMesh {

    private float radius;
    private float[][] ro;

    public TwelveSidedMesh(float radius) {

        this.radius = radius;

        /**
         Algorithm adapted from: http://userpages.umbc.edu/~squire/reference/polyhedra.shtml#dodecahedron
         **/

        float verts[][] = new float[20][3];
        double pi = Math.PI;
        double phiaa = 52.62263590;
        double phibb = 10.81231754;

        double phia = pi * phiaa / 180.0; /* 4 sets of five points each */
        double phib = pi * phibb / 180.0;
        double phic = pi * (-phibb) / 180.0;
        double phid = pi * (-phiaa) / 180.0;
        double the72 = pi * 72.0 / 180.0;
        double theb = the72 / 2.0; /* pairs of layers offset 36 degrees */
        double the = 0.0;

        Log.d("Values", Double.toString((radius * Math.cos(the) * Math.cos(phia))));

        for (int i = 0; i < 5; i++) {
            verts[i][0] = (float) (radius * Math.cos(the) * Math.cos(phia));
            verts[i][1] = (float) (radius * Math.sin(the) * Math.cos(phia));
            verts[i][2] = (float) (radius * Math.sin(phia));
            the = the + the72;
        }

        the = 0.0;

        for (int i = 5; i < 10; i++) {
            verts[i][0] = (float) (radius * Math.cos(the)* Math.cos(phib));
            verts[i][1] = (float) (radius * Math.sin(the) * Math.cos(phib));
            verts[i][2] = (float) (radius * Math.sin(phib));
            the = the+the72;
        }

        the = theb;

        for(int i= 10; i < 15; i++) {
            verts[i][0]= (float) (radius * Math.cos(the) * Math.cos(phic));
            verts[i][1]= (float) (radius * Math.sin(the) * Math.cos(phic));
            verts[i][2]= (float) (radius * Math.sin(phic));
            the = the+the72;
        }

        the=theb;

        for(int i = 15; i < 20; i++) {
            verts[i][0]= (float) (radius * Math.cos(the) * Math.cos(phid));
            verts[i][1]= (float) (radius * Math.sin(the) * Math.cos(phid));
            verts[i][2]= (float) (radius * Math.sin(phid));
            the = the+the72;
        }

        for (int i = 0; i < 20; i++) {
            Log.d("Values", "x" + Integer.toString(i) + ": " + Float.toString(verts[i][0]) +
                    " y" + Integer.toString(i) + ": " + Float.toString(verts[i][1]) +
                    " z" + Integer.toString(i) + ": " + Float.toString(verts[i][2]));
        }

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 3072.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 3072.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 3072.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 3072.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 3072.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 3072.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 3072.0f;
        float faceFiveLeftBound = (256.0f * 4.0f + 1.0f) / 3072.0f;
        float faceFiveRightBound = (256.0f * 5.0f) / 3072.0f;
        float faceSixLeftBound = (256.0f * 5.0f + 1.0f) / 3072.0f;
        float faceSixRightBound = (256.0f * 6.0f) / 3072.0f;
        float faceSevenLeftBound = (256.0f * 6.0f + 1.0f) / 3072.0f;
        float faceSevenRightBound = (256.0f * 7.0f) / 3072.0f;
        float faceEightLeftBound = (256.0f * 7.0f + 1.0f) / 3072.0f;
        float faceEightRightBound = (256.0f * 8.0f) / 3072.0f;
        float faceNineLeftBound = (256.0f * 8.0f + 1.0f) / 3072.0f;
        float faceNineRightBound = (256.0f * 9.0f) / 3072.0f;
        float faceTenLeftBound = (256.0f * 9.0f + 1.0f) / 3072.0f;
        float faceTenRightBound = (256.0f * 10.0f) / 3072.0f;
        float faceElevenLeftBound = (256.0f * 10.0f + 1.0f) / 3072.0f;
        float faceElevenRightBound = (256.0f * 11.0f) / 3072.0f;
        float faceTwelveLeftBound = (256.0f * 11.0f + 1.0f) / 3072.0f;
        float faceTwelveRightBound = (256.0f * 12.0f) / 3072.0f;
        float faceBoundHalfModifier = (128.0f / 3072.0f);
        float faceFifthModifier = (51.2f / 3072.0f);

        float vertices[] =
                {
                        verts[2][0], verts[2][1], verts[2][2], //1 Face
                        verts[3][0], verts[3][1], verts[3][2],
                        verts[1][0], verts[1][1], verts[1][2],

                        verts[3][0], verts[3][1], verts[3][2],
                        verts[4][0], verts[4][1], verts[4][2],
                        verts[1][0], verts[1][1], verts[1][2],

                        verts[1][0], verts[1][1], verts[1][2],
                        verts[4][0], verts[4][1], verts[4][2],
                        verts[0][0], verts[0][1], verts[0][2],

                        verts[5][0], verts[5][1], verts[5][2], //2 Face
                        verts[0][0], verts[0][1], verts[0][2],
                        verts[14][0], verts[14][1], verts[14][2],

                        verts[0][0], verts[0][1], verts[0][2],
                        verts[4][0], verts[4][1], verts[4][2],
                        verts[14][0], verts[14][1], verts[14][2],

                        verts[14][0], verts[14][1], verts[14][2],
                        verts[4][0], verts[4][1], verts[4][2],
                        verts[9][0], verts[9][1], verts[9][2],

                        verts[12][0], verts[12][1], verts[12][2], //3 Face
                        verts[17][0], verts[17][1], verts[17][2],
                        verts[8][0], verts[8][1], verts[8][2],

                        verts[17][0], verts[17][1], verts[17][2],
                        verts[18][0], verts[18][1], verts[18][2],
                        verts[8][0], verts[8][1], verts[8][2],

                        verts[8][0], verts[8][1], verts[8][2],
                        verts[18][0], verts[18][1], verts[18][2],
                        verts[13][0], verts[13][1], verts[13][2],

                        verts[9][0], verts[9][1], verts[9][2], //4 Face
                        verts[4][0], verts[4][1], verts[4][2],
                        verts[13][0], verts[13][1], verts[13][2],

                        verts[4][0], verts[4][1], verts[4][2],
                        verts[3][0], verts[3][1], verts[3][2],
                        verts[13][0], verts[13][1], verts[13][2],

                        verts[13][0], verts[13][1], verts[13][2],
                        verts[3][0], verts[3][1], verts[3][2],
                        verts[8][0], verts[8][1], verts[8][2],

                        verts[7][0], verts[7][1], verts[7][2], //5 Face
                        verts[2][0], verts[2][1], verts[2][2],
                        verts[11][0], verts[11][1], verts[11][2],

                        verts[2][0], verts[2][1], verts[2][2],
                        verts[1][0], verts[1][1], verts[1][2],
                        verts[11][0], verts[11][1], verts[11][2],

                        verts[11][0], verts[11][1], verts[11][2],
                        verts[1][0], verts[1][1], verts[1][2],
                        verts[6][0], verts[6][1], verts[6][2],

                        verts[8][0], verts[8][1], verts[8][2], //6 Face
                        verts[3][0], verts[3][1], verts[3][2],
                        verts[12][0], verts[12][1], verts[12][2],

                        verts[3][0], verts[3][1], verts[3][2],
                        verts[2][0], verts[2][1], verts[2][2],
                        verts[12][0], verts[12][1], verts[12][2],

                        verts[12][0], verts[12][1], verts[12][2],
                        verts[2][0], verts[2][1], verts[2][2],
                        verts[7][0], verts[7][1], verts[7][2],

                        verts[14][0], verts[14][1], verts[14][2], //7 Face
                        verts[19][0], verts[19][1], verts[19][2],
                        verts[5][0], verts[5][1], verts[5][2],

                        verts[19][0], verts[19][1], verts[19][2],
                        verts[15][0], verts[15][1], verts[15][2],
                        verts[5][0], verts[5][1], verts[5][2],

                        verts[5][0], verts[5][1], verts[5][2],
                        verts[15][0], verts[15][1], verts[15][2],
                        verts[10][0], verts[10][1], verts[10][2],

                        verts[13][0], verts[13][1], verts[13][2], //8 Face
                        verts[18][0], verts[18][1], verts[18][2],
                        verts[9][0], verts[9][1], verts[9][2],

                        verts[18][0], verts[18][1], verts[18][2],
                        verts[19][0], verts[19][1], verts[19][2],
                        verts[9][0], verts[9][1], verts[9][2],

                        verts[9][0], verts[9][1], verts[9][2],
                        verts[19][0], verts[19][1], verts[19][2],
                        verts[14][0], verts[14][1], verts[14][2],

                        verts[10][0], verts[10][1], verts[10][2], //9 Face
                        verts[15][0], verts[15][1], verts[15][2],
                        verts[6][0], verts[6][1], verts[6][2],

                        verts[15][0], verts[15][1], verts[15][2],
                        verts[16][0], verts[16][1], verts[16][2],
                        verts[6][0], verts[6][1], verts[6][2],

                        verts[6][0], verts[6][1], verts[6][2],
                        verts[16][0], verts[16][1], verts[16][2],
                        verts[11][0], verts[11][1], verts[11][2],

                        verts[6][0], verts[6][1], verts[6][2], //10 Face
                        verts[1][0], verts[1][1], verts[1][2],
                        verts[10][0], verts[10][1], verts[10][2],

                        verts[1][0], verts[1][1], verts[1][2],
                        verts[0][0], verts[0][1], verts[0][2],
                        verts[10][0], verts[10][1], verts[10][2],

                        verts[10][0], verts[10][1], verts[10][2],
                        verts[0][0], verts[0][1], verts[0][2],
                        verts[5][0], verts[5][1], verts[5][2],

                        verts[11][0], verts[11][1], verts[11][2], //11 Face
                        verts[16][0], verts[16][1], verts[16][2],
                        verts[7][0], verts[7][1], verts[7][2],

                        verts[16][0], verts[16][1], verts[16][2],
                        verts[17][0], verts[17][1], verts[17][2],
                        verts[7][0], verts[7][1], verts[7][2],

                        verts[7][0], verts[7][1], verts[7][2],
                        verts[17][0], verts[17][1], verts[17][2],
                        verts[12][0], verts[12][1], verts[12][2],

                        verts[17][0], verts[17][1], verts[17][2], //12 Face
                        verts[16][0], verts[16][1], verts[16][2],
                        verts[18][0], verts[18][1], verts[18][2],

                        verts[16][0], verts[16][1], verts[16][2],
                        verts[15][0], verts[15][1], verts[15][2],
                        verts[18][0], verts[18][1], verts[18][2],

                        verts[18][0], verts[18][1], verts[18][2],
                        verts[15][0], verts[15][1], verts[15][2],
                        verts[19][0], verts[19][1], verts[19][2],
                };

        float centroids[][] = {

                {((verts[3][0] + verts[4][0] + verts[1][0]) / 3),
                        ((verts[3][1] + verts[4][1] + verts[1][1]) / 3),
                        ((verts[3][2] + verts[4][2] + verts[1][2]) / 3)},

                {((verts[0][0] + verts[4][0] + verts[14][0]) / 3),
                        ((verts[0][1] + verts[4][1] + verts[14][1]) / 3),
                        ((verts[0][2] + verts[4][2] + verts[14][2]) / 3)},

                {((verts[17][0] + verts[18][0] + verts[8][0]) / 3),
                        ((verts[17][1] + verts[18][1] + verts[8][1]) / 3),
                        ((verts[17][2] + verts[18][2] + verts[8][2]) / 3)},

                {((verts[4][0] + verts[3][0] + verts[13][0]) / 3),
                        ((verts[4][1] + verts[3][1] + verts[13][1]) / 3),
                        ((verts[4][2] + verts[3][2] + verts[13][2]) / 3)},

                {((verts[2][0] + verts[1][0] + verts[11][0]) / 3),
                        ((verts[2][1] + verts[1][1] + verts[11][1]) / 3),
                        ((verts[2][2] + verts[1][2] + verts[11][2]) / 3)},

                {((verts[3][0] + verts[2][0] + verts[12][0]) / 3),
                        ((verts[3][1] + verts[2][1] + verts[12][1]) / 3),
                        ((verts[3][2] + verts[2][2] + verts[12][2]) / 3)},

                {((verts[19][0] + verts[15][0] + verts[5][0]) / 3),
                        ((verts[19][1] + verts[15][1] + verts[5][1]) / 3),
                        ((verts[19][2] + verts[15][2] + verts[5][2]) / 3)},

                {((verts[18][0] + verts[19][0] + verts[9][0]) / 3),
                        ((verts[18][1] + verts[19][1] + verts[9][1]) / 3),
                        ((verts[18][2] + verts[19][2] + verts[9][2]) / 3)},

                {((verts[15][0] + verts[16][0] + verts[6][0]) / 3),
                        ((verts[15][1] + verts[16][1] + verts[6][1]) / 3),
                        ((verts[15][2] + verts[16][2] + verts[6][2]) / 3)},

                {((verts[1][0] + verts[0][0] + verts[10][0]) / 3),
                        ((verts[1][1] + verts[0][1] + verts[10][1]) / 3),
                        ((verts[1][2] + verts[0][2] + verts[10][2]) / 3)},

                {((verts[16][0] + verts[17][0] + verts[7][0]) / 3),
                        ((verts[16][1] + verts[17][1] + verts[7][1]) / 3),
                        ((verts[16][2] + verts[17][2] + verts[7][2]) / 3)},

                {((verts[16][0] + verts[15][0] + verts[18][0]) / 3),
                        ((verts[16][1] + verts[15][1] + verts[18][1]) / 3),
                        ((verts[16][2] + verts[15][2] + verts[18][2]) / 3)}

        };

        float ro[][] = {

                normalize(centroids[0]),
                normalize(centroids[1]),
                normalize(centroids[2]),
                normalize(centroids[3]),
                normalize(centroids[4]),
                normalize(centroids[5]),
                normalize(centroids[6]),
                normalize(centroids[7]),
                normalize(centroids[8]),
                normalize(centroids[9]),
                normalize(centroids[10]),
                normalize(centroids[11])

        };

        this.ro = ro;

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
                        57, 58, 59,
                        60, 61, 62,
                        63, 64, 65,
                        66, 67, 68,
                        69, 70, 71,
                        72, 73, 74,
                        75, 76, 77,
                        78, 79, 80,
                        81, 82, 83,
                        84, 85, 86,
                        87, 88, 89,
                        90, 91, 92,
                        93, 94, 95,
                        96, 97, 98,
                        99, 100, 101,
                        102, 103, 104,
                        105, 106, 107
                };

        float textureCoordinates[] =
                {
                        faceOneLeftBound, (2.0f / 5.0f), //1 Face
                        faceOneLeftBound + faceFifthModifier, 1.0f,
                        faceOneLeftBound + faceBoundHalfModifier, 0.0f,

                        faceOneLeftBound + faceFifthModifier, 1.0f,
                        faceOneRightBound - faceFifthModifier, 1.0f,
                        faceOneLeftBound + faceBoundHalfModifier, 0.0f,

                        faceOneLeftBound + faceBoundHalfModifier, 0.0f,
                        faceOneRightBound - faceFifthModifier, 1.0f,
                        faceOneRightBound, (3.0f / 5.0f),

                        faceTwoLeftBound, (2.0f / 5.0f), //2 Face
                        faceTwoLeftBound + faceFifthModifier, 1.0f,
                        faceTwoLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwoLeftBound + faceFifthModifier, 1.0f,
                        faceTwoRightBound - faceFifthModifier, 1.0f,
                        faceTwoLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwoLeftBound + faceBoundHalfModifier, 0.0f,
                        faceTwoRightBound - faceFifthModifier, 1.0f,
                        faceTwoRightBound, (3.0f / 5.0f),

                        faceThreeLeftBound, (2.0f / 5.0f), //3 Face
                        faceThreeLeftBound + faceFifthModifier, 1.0f,
                        faceThreeLeftBound + faceBoundHalfModifier, 0.0f,

                        faceThreeLeftBound + faceFifthModifier, 1.0f,
                        faceThreeRightBound - faceFifthModifier, 1.0f,
                        faceThreeLeftBound + faceBoundHalfModifier, 0.0f,

                        faceThreeLeftBound + faceBoundHalfModifier, 0.0f,
                        faceThreeRightBound - faceFifthModifier, 1.0f,
                        faceThreeRightBound, (3.0f / 5.0f),

                        faceFourLeftBound, (2.0f / 5.0f), //4 Face
                        faceFourLeftBound + faceFifthModifier, 1.0f,
                        faceFourLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFourLeftBound + faceFifthModifier, 1.0f,
                        faceFourRightBound - faceFifthModifier, 1.0f,
                        faceFourLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFourLeftBound + faceBoundHalfModifier, 0.0f,
                        faceFourRightBound - faceFifthModifier, 1.0f,
                        faceFourRightBound, (3.0f / 5.0f),

                        faceFiveLeftBound, (2.0f / 5.0f), //5 Face
                        faceFiveLeftBound + faceFifthModifier, 1.0f,
                        faceFiveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFiveLeftBound + faceFifthModifier, 1.0f,
                        faceFiveRightBound - faceFifthModifier, 1.0f,
                        faceFiveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceFiveLeftBound + faceBoundHalfModifier, 0.0f,
                        faceFiveRightBound - faceFifthModifier, 1.0f,
                        faceFiveRightBound, (3.0f / 5.0f),

                        faceSixLeftBound, (2.0f / 5.0f), //6 Face
                        faceSixLeftBound + faceFifthModifier, 1.0f,
                        faceSixLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSixLeftBound + faceFifthModifier, 1.0f,
                        faceSixRightBound - faceFifthModifier, 1.0f,
                        faceSixLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSixLeftBound + faceBoundHalfModifier, 0.0f,
                        faceSixRightBound - faceFifthModifier, 1.0f,
                        faceSixRightBound, (3.0f / 5.0f),

                        faceSevenLeftBound, (2.0f / 5.0f), //7 Face
                        faceSevenLeftBound + faceFifthModifier, 1.0f,
                        faceSevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSevenLeftBound + faceFifthModifier, 1.0f,
                        faceSevenRightBound - faceFifthModifier, 1.0f,
                        faceSevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceSevenLeftBound + faceBoundHalfModifier, 0.0f,
                        faceSevenRightBound - faceFifthModifier, 1.0f,
                        faceSevenRightBound, (3.0f / 5.0f),

                        faceEightLeftBound, (2.0f / 5.0f), //8 Face
                        faceEightLeftBound + faceFifthModifier, 1.0f,
                        faceEightLeftBound + faceBoundHalfModifier, 0.0f,

                        faceEightLeftBound + faceFifthModifier, 1.0f,
                        faceEightRightBound - faceFifthModifier, 1.0f,
                        faceEightLeftBound + faceBoundHalfModifier, 0.0f,

                        faceEightLeftBound + faceBoundHalfModifier, 0.0f,
                        faceEightRightBound - faceFifthModifier, 1.0f,
                        faceEightRightBound, (3.0f / 5.0f),

                        faceNineLeftBound, (2.0f / 5.0f), //9 Face
                        faceNineLeftBound + faceFifthModifier, 1.0f,
                        faceNineLeftBound + faceBoundHalfModifier, 0.0f,

                        faceNineLeftBound + faceFifthModifier, 1.0f,
                        faceNineRightBound - faceFifthModifier, 1.0f,
                        faceNineLeftBound + faceBoundHalfModifier, 0.0f,

                        faceNineLeftBound + faceBoundHalfModifier, 0.0f,
                        faceNineRightBound - faceFifthModifier, 1.0f,
                        faceNineRightBound, (3.0f / 5.0f),

                        faceTenLeftBound, (2.0f / 5.0f), //10 Face
                        faceTenLeftBound + faceFifthModifier, 1.0f,
                        faceTenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTenLeftBound + faceFifthModifier, 1.0f,
                        faceTenRightBound - faceFifthModifier, 1.0f,
                        faceTenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTenLeftBound + faceBoundHalfModifier, 0.0f,
                        faceTenRightBound - faceFifthModifier, 1.0f,
                        faceTenRightBound, (3.0f / 5.0f),

                        faceElevenLeftBound, (2.0f / 5.0f), //11 Face
                        faceElevenLeftBound + faceFifthModifier, 1.0f,
                        faceElevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceElevenLeftBound + faceFifthModifier, 1.0f,
                        faceElevenRightBound - faceFifthModifier, 1.0f,
                        faceElevenLeftBound + faceBoundHalfModifier, 0.0f,

                        faceElevenLeftBound + faceBoundHalfModifier, 0.0f,
                        faceElevenRightBound - faceFifthModifier, 1.0f,
                        faceElevenRightBound, (3.0f / 5.0f),

                        faceTwelveLeftBound, (2.0f / 5.0f), //12 Face
                        faceTwelveLeftBound + faceFifthModifier, 1.0f,
                        faceTwelveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwelveLeftBound + faceFifthModifier, 1.0f,
                        faceTwelveRightBound - faceFifthModifier, 1.0f,
                        faceTwelveLeftBound + faceBoundHalfModifier, 0.0f,

                        faceTwelveLeftBound + faceBoundHalfModifier, 0.0f,
                        faceTwelveRightBound - faceFifthModifier, 1.0f,
                        faceTwelveRightBound, (3.0f / 5.0f)
                };

        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }

    @Override
    public float getRadius() {
        return radius * 0.8f;
    }

    @Override
    public float[] getFlatteningEuelerAngles () {

        float[] flatteningEulerAngles = new float[3];

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
                {dot(Ro2w[0], ro[6]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[7]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[8]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[9]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[10]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])},
                {dot(Ro2w[0], ro[11]), dot(Ro2w[1], ro[0]), dot(Ro2w[2], ro[0])}

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
                (float) (Math.acos(dot(nw[9], down))),
                (float) (Math.acos(dot(nw[10], down))),
                (float) (Math.acos(dot(nw[11], down)))

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
