package edu.okami.m.dicebuilder;

public class EightSidedMesh extends DiceMesh {

    public EightSidedMesh(float width) {

        float halfHeight = (float) Math.sqrt((width * width) - ((width/2) * (width/2)));
        float halfWidth = width / 2;

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

}
