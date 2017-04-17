package edu.okami.m.dicebuilder;

public class TwentySidedMesh extends DiceMesh {

    public TwentySidedMesh(float scaleFactor) {

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

}
