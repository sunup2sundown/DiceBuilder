package edu.okami.m.dicebuilder;

public class SixSidedMesh extends DiceMesh {

    public SixSidedMesh(float width) {

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

}
