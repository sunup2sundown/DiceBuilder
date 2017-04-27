package edu.okami.m.dicebuilder;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class TwentySidedMesh extends DiceMesh {

    private float scaleFactor;
    private float[][] ro;

    public TwentySidedMesh(float scaleFactor) {

        this.scaleFactor = scaleFactor;

        double squareRootOfFive = Math.sqrt(5);
        float goldenRatio = (float) ((1 + squareRootOfFive)/2);

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 5120.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 5120.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 5120.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 5120.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 5120.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 5120.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 5120.0f;
        float faceFiveLeftBound = (256.0f * 4.0f + 1.0f) / 5120.0f;
        float faceFiveRightBound = (256.0f * 5.0f) / 5120.0f;
        float faceSixLeftBound = (256.0f * 5.0f + 1.0f) / 5120.0f;
        float faceSixRightBound = (256.0f * 6.0f) / 5120.0f;
        float faceSevenLeftBound = (256.0f * 6.0f + 1.0f) / 5120.0f;
        float faceSevenRightBound = (256.0f * 7.0f) / 5120.0f;
        float faceEightLeftBound = (256.0f * 7.0f + 1.0f) / 5120.0f;
        float faceEightRightBound = (256.0f * 8.0f) / 5120.0f;
        float faceNineLeftBound = (256.0f * 8.0f + 1.0f) / 5120.0f;
        float faceNineRightBound = (256.0f * 9.0f) / 5120.0f;
        float faceTenLeftBound = (256.0f * 9.0f + 1.0f) / 5120.0f;
        float faceTenRightBound = (256.0f * 10.0f) / 5120.0f;
        float faceElevenLeftBound = (256.0f * 10.0f + 1.0f) / 5120.0f;
        float faceElevenRightBound = (256.0f * 11.0f) / 5120.0f;
        float faceTwelveLeftBound = (256.0f * 11.0f + 1.0f) / 5120.0f;
        float faceTwelveRightBound = (256.0f * 12.0f) / 5120.0f;
        float faceThirteenLeftBound = (256.0f * 12.0f + 1.0f) / 5120.0f;
        float faceThirteenRightBound = (256.0f * 13.0f) / 5120.0f;
        float faceFourteenLeftBound = (256.0f * 13.0f + 1.0f) / 5120.0f;
        float faceFourteenRightBound = (256.0f * 14.0f) / 5120.0f;
        float faceFifteenLeftBound = (256.0f * 14.0f + 1.0f) / 5120.0f;
        float faceFifteenRightBound = (256.0f * 15.0f) / 5120.0f;
        float faceSixteenLeftBound = (256.0f * 15.0f + 1.0f) / 5120.0f;
        float faceSixteenRightBound = (256.0f * 16.0f) / 5120.0f;
        float faceSeventeenLeftBound = (256.0f * 16.0f + 1.0f) / 5120.0f;
        float faceSeventeenRightBound = (256.0f * 17.0f) / 5120.0f;
        float faceEighteenLeftBound = (256.0f * 17.0f + 1.0f) / 5120.0f;
        float faceEighteenRightBound = (256.0f * 18.0f) / 5120.0f;
        float faceNineteenLeftBound = (256.0f * 18.0f + 1.0f) / 5120.0f;
        float faceNineteenRightBound = (256.0f * 19.0f) / 5120.0f;
        float faceTwentyLeftBound = (256.0f * 19.0f + 1.0f) / 5120.0f;
        float faceTwentyRightBound = (256.0f * 20.0f) / 5120.0f;
        float faceBoundHalfModifier = (128.0f / 5120.0f);

        float vertices[] =
                {
                        -scaleFactor, 0.0f, (goldenRatio * scaleFactor), //0 //1 Face
                        0.0f, (goldenRatio * scaleFactor), scaleFactor, //4
                        scaleFactor, 0.0f, (goldenRatio * scaleFactor), //1

                        0.0f, -(goldenRatio * scaleFactor), -scaleFactor, //7 //2 Face
                        -scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //2
                        -(goldenRatio * scaleFactor), -scaleFactor, 0.0f, //11

                        scaleFactor, 0.0f, (goldenRatio * scaleFactor), //1 //3 Face
                        (goldenRatio * scaleFactor), scaleFactor, 0.0f, //8
                        (goldenRatio * scaleFactor), -scaleFactor, 0.0f, //10

                        -scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //2 //4 Face
                        0.0f, (goldenRatio * scaleFactor), -scaleFactor, //5
                        -(goldenRatio * scaleFactor), scaleFactor, 0.0f, //9

                        -(goldenRatio * scaleFactor), scaleFactor, 0.0f, //9 //5 Face
                        -scaleFactor, 0.0f, (goldenRatio * scaleFactor), //0
                        -(goldenRatio * scaleFactor), -scaleFactor, 0.0f, //11

                        0.0f, (goldenRatio * scaleFactor), -scaleFactor, //5 //6 Face
                        scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //3
                        (goldenRatio * scaleFactor), scaleFactor, 0.0f, //8

                        -scaleFactor, 0.0f, (goldenRatio * scaleFactor), //0 //7 Face
                        scaleFactor, 0.0f, (goldenRatio * scaleFactor), //1
                        0.0f, -(goldenRatio * scaleFactor), scaleFactor, //6

                        scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //3 //8 Face
                        0.0f, -(goldenRatio * scaleFactor), -scaleFactor, //7
                        (goldenRatio * scaleFactor), -scaleFactor, 0.0f, //10

                        (goldenRatio * scaleFactor), scaleFactor, 0.0f, //8 //9 Face
                        0.0f, (goldenRatio * scaleFactor), scaleFactor, //4
                        0.0f, (goldenRatio * scaleFactor), -scaleFactor, //5

                        (goldenRatio * scaleFactor), -scaleFactor, 0.0f, //10 //10 Face
                        0.0f, -(goldenRatio * scaleFactor), -scaleFactor, //7
                        0.0f, -(goldenRatio * scaleFactor), scaleFactor, //6

                        0.0f, (goldenRatio * scaleFactor), scaleFactor, //4  //11 Face
                        -(goldenRatio * scaleFactor), scaleFactor, 0.0f, //9
                        0.0f, (goldenRatio * scaleFactor), -scaleFactor, //5

                        0.0f, -(goldenRatio * scaleFactor), -scaleFactor, //7 //12 Face
                        -(goldenRatio * scaleFactor), -scaleFactor, 0.0f, //11
                        0.0f, -(goldenRatio * scaleFactor), scaleFactor, //6

                        0.0f, (goldenRatio * scaleFactor), scaleFactor, //4 //13 Face
                        -scaleFactor, 0.0f, (goldenRatio * scaleFactor), //0
                        -(goldenRatio * scaleFactor), scaleFactor, 0.0f, //9

                        -scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //2 //14 Face
                        scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //3
                        0.0f, (goldenRatio * scaleFactor), -scaleFactor, //5

                        -scaleFactor, 0.0f, (goldenRatio * scaleFactor), //0 //15 Face
                        0.0f, -(goldenRatio * scaleFactor), scaleFactor, //6
                        -(goldenRatio * scaleFactor), -scaleFactor, 0.0f, //11

                        scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //3 //16 Face
                        (goldenRatio * scaleFactor), -scaleFactor, 0.0f, //10
                        (goldenRatio * scaleFactor), scaleFactor, 0.0f, //8

                        0.0f, -(goldenRatio * scaleFactor), scaleFactor, //6 //17 Face
                        scaleFactor, 0.0f, (goldenRatio * scaleFactor), //1
                        (goldenRatio * scaleFactor), -scaleFactor, 0.0f, //10

                        -(goldenRatio * scaleFactor), -scaleFactor, 0.0f, //11 //18 Face
                        -scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //2
                        -(goldenRatio * scaleFactor), scaleFactor, 0.0f, //9

                        scaleFactor, 0.0f, (goldenRatio * scaleFactor), //1 //19 Face
                        0.0f, (goldenRatio * scaleFactor), scaleFactor, //4
                        (goldenRatio * scaleFactor), scaleFactor, 0.0f, //8

                        0.0f, -(goldenRatio * scaleFactor), -scaleFactor, //7 //20 Face
                        scaleFactor, 0.0f, -(goldenRatio * scaleFactor), //3
                        -scaleFactor, 0.0f, -(goldenRatio * scaleFactor) //2
                };

        float[][] centroids = {

                {((vertices[0] + vertices[3] + vertices[6]) / 3), //1
                        ((vertices[1] + vertices[4] + vertices[7]) / 3),
                        ((vertices[2] + vertices[5] + vertices[8]) / 3)},

                {((vertices[9] + vertices[12] + vertices[15]) / 3), //2
                        ((vertices[10] + vertices[13] + vertices[16]) / 3),
                        ((vertices[11] + vertices[14] + vertices[17]) / 3)},

                {((vertices[18] + vertices[21] + vertices[24]) / 3), //3
                        ((vertices[19] + vertices[22] + vertices[25]) / 3),
                        ((vertices[20] + vertices[23] + vertices[26]) / 3)},

                {((vertices[27] + vertices[30] + vertices[33]) / 3), //4
                        ((vertices[28] + vertices[31] + vertices[34]) / 3),
                        ((vertices[29] + vertices[32] + vertices[35]) / 3)},

                {((vertices[36] + vertices[39] + vertices[42]) / 3), //5
                        ((vertices[37] + vertices[40] + vertices[43]) / 3),
                        ((vertices[38] + vertices[41] + vertices[44]) / 3)},

                {((vertices[45] + vertices[48] + vertices[51]) / 3), //6
                        ((vertices[46] + vertices[49] + vertices[52]) / 3),
                        ((vertices[47] + vertices[50] + vertices[53]) / 3)},

                {((vertices[54] + vertices[57] + vertices[60]) / 3), //7
                        ((vertices[55] + vertices[58] + vertices[61]) / 3),
                        ((vertices[56] + vertices[59] + vertices[62]) / 3)},

                {((vertices[63] + vertices[66] + vertices[69]) / 3), //8
                        ((vertices[64] + vertices[67] + vertices[70]) / 3),
                        ((vertices[65] + vertices[68] + vertices[71]) / 3)},

                {((vertices[72] + vertices[75] + vertices[78]) / 3), //9
                        ((vertices[73] + vertices[76] + vertices[79]) / 3),
                        ((vertices[74] + vertices[77] + vertices[80]) / 3)},

                {((vertices[81] + vertices[84] + vertices[87]) / 3), //10
                        ((vertices[82] + vertices[85] + vertices[88]) / 3),
                        ((vertices[83] + vertices[86] + vertices[89]) / 3)},

                {((vertices[90] + vertices[93] + vertices[96]) / 3), //11
                        ((vertices[91] + vertices[94] + vertices[97]) / 3),
                        ((vertices[92] + vertices[95] + vertices[98]) / 3)},

                {((vertices[99] + vertices[102] + vertices[105]) / 3), //12
                        ((vertices[100] + vertices[103] + vertices[106]) / 3),
                        ((vertices[101] + vertices[104] + vertices[107]) / 3)},

                {((vertices[108] + vertices[111] + vertices[114]) / 3), //13
                        ((vertices[109] + vertices[112] + vertices[115]) / 3),
                        ((vertices[110] + vertices[113] + vertices[116]) / 3)},

                {((vertices[117] + vertices[120] + vertices[123]) / 3), //14
                        ((vertices[118] + vertices[121] + vertices[124]) / 3),
                        ((vertices[119] + vertices[122] + vertices[125]) / 3)},

                {((vertices[126] + vertices[129] + vertices[132]) / 3), //15
                        ((vertices[127] + vertices[130] + vertices[133]) / 3),
                        ((vertices[128] + vertices[131] + vertices[134]) / 3)},

                {((vertices[135] + vertices[138] + vertices[141]) / 3), //16
                        ((vertices[136] + vertices[139] + vertices[142]) / 3),
                        ((vertices[137] + vertices[140] + vertices[143]) / 3)},

                {((vertices[144] + vertices[147] + vertices[150]) / 3), //17
                        ((vertices[145] + vertices[148] + vertices[151]) / 3),
                        ((vertices[146] + vertices[149] + vertices[152]) / 3)},

                {((vertices[153] + vertices[156] + vertices[159]) / 3), //18
                        ((vertices[154] + vertices[157] + vertices[160]) / 3),
                        ((vertices[155] + vertices[158] + vertices[161]) / 3)},

                {((vertices[162] + vertices[165] + vertices[168]) / 3), //19
                        ((vertices[163] + vertices[166] + vertices[169]) / 3),
                        ((vertices[164] + vertices[167] + vertices[170]) / 3)},

                {((vertices[171] + vertices[174] + vertices[177]) / 3), //20
                        ((vertices[172] + vertices[175] + vertices[178]) / 3),
                        ((vertices[173] + vertices[176] + vertices[179]) / 3)}

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
                normalize(centroids[9]),
                normalize(centroids[10]),
                normalize(centroids[11]),
                normalize(centroids[12]),
                normalize(centroids[13]),
                normalize(centroids[14]),
                normalize(centroids[15]),
                normalize(centroids[16]),
                normalize(centroids[17]),
                normalize(centroids[18]),
                normalize(centroids[19])

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
                        57, 58, 59
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
                        (faceEightLeftBound + faceBoundHalfModifier), 0.0f,

                        faceNineLeftBound, 1.0f,
                        faceNineRightBound, 1.0f,
                        (faceNineLeftBound + faceBoundHalfModifier), 0.0f,

                        faceTenLeftBound, 1.0f,
                        faceTenRightBound, 1.0f,
                        (faceTenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceElevenLeftBound, 1.0f,
                        faceElevenRightBound, 1.0f,
                        (faceElevenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceTwelveLeftBound, 1.0f,
                        faceTwelveRightBound, 1.0f,
                        (faceTwelveLeftBound + faceBoundHalfModifier), 0.0f,

                        faceThirteenLeftBound, 1.0f,
                        faceThirteenRightBound, 1.0f,
                        (faceThirteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceFourteenLeftBound, 1.0f,
                        faceFourteenRightBound, 1.0f,
                        (faceFourteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceFifteenLeftBound, 1.0f,
                        faceFifteenRightBound, 1.0f,
                        (faceFifteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceSixteenLeftBound, 1.0f,
                        faceSixteenRightBound, 1.0f,
                        (faceSixteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceSeventeenLeftBound, 1.0f,
                        faceSeventeenRightBound, 1.0f,
                        (faceSeventeenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceEighteenLeftBound, 1.0f,
                        faceEighteenRightBound, 1.0f,
                        (faceEighteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceNineteenLeftBound, 1.0f,
                        faceNineteenRightBound, 1.0f,
                        (faceNineteenLeftBound + faceBoundHalfModifier), 0.0f,

                        faceTwentyLeftBound, 1.0f,
                        faceTwentyRightBound, 1.0f,
                        (faceTwentyLeftBound + faceBoundHalfModifier), 0.0f
                };

        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }

    @Override
    public float getRadius() {
        return scaleFactor * 1.5f;
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
                {dot(Ro2w[0], ro[6]), dot(Ro2w[1], ro[6]), dot(Ro2w[2], ro[6])},
                {dot(Ro2w[0], ro[7]), dot(Ro2w[1], ro[7]), dot(Ro2w[2], ro[7])},
                {dot(Ro2w[0], ro[8]), dot(Ro2w[1], ro[8]), dot(Ro2w[2], ro[8])},
                {dot(Ro2w[0], ro[9]), dot(Ro2w[1], ro[9]), dot(Ro2w[2], ro[9])},
                {dot(Ro2w[0], ro[10]), dot(Ro2w[1], ro[10]), dot(Ro2w[2], ro[10])},
                {dot(Ro2w[0], ro[11]), dot(Ro2w[1], ro[11]), dot(Ro2w[2], ro[11])},
                {dot(Ro2w[0], ro[12]), dot(Ro2w[1], ro[12]), dot(Ro2w[2], ro[12])},
                {dot(Ro2w[0], ro[13]), dot(Ro2w[1], ro[13]), dot(Ro2w[2], ro[13])},
                {dot(Ro2w[0], ro[14]), dot(Ro2w[1], ro[14]), dot(Ro2w[2], ro[14])},
                {dot(Ro2w[0], ro[15]), dot(Ro2w[1], ro[15]), dot(Ro2w[2], ro[15])},
                {dot(Ro2w[0], ro[16]), dot(Ro2w[1], ro[16]), dot(Ro2w[2], ro[16])},
                {dot(Ro2w[0], ro[17]), dot(Ro2w[1], ro[17]), dot(Ro2w[2], ro[17])},
                {dot(Ro2w[0], ro[18]), dot(Ro2w[1], ro[18]), dot(Ro2w[2], ro[18])},
                {dot(Ro2w[0], ro[19]), dot(Ro2w[1], ro[19]), dot(Ro2w[2], ro[19])}

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
                (float) (Math.acos(dot(nw[11], down))),
                (float) (Math.acos(dot(nw[12], down))),
                (float) (Math.acos(dot(nw[13], down))),
                (float) (Math.acos(dot(nw[14], down))),
                (float) (Math.acos(dot(nw[15], down))),
                (float) (Math.acos(dot(nw[16], down))),
                (float) (Math.acos(dot(nw[17], down))),
                (float) (Math.acos(dot(nw[18], down))),
                (float) (Math.acos(dot(nw[19], down)))

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
