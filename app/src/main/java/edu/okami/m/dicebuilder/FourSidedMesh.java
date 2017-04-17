package edu.okami.m.dicebuilder;

public class FourSidedMesh extends DiceMesh {

    public FourSidedMesh(float width, float height, float depth) {

        float halfWidth = width / 2;
        float halfHeight = height / 2;
        float halfDepth = depth / 2;

        float faceOneLeftBound = 0.0f;
        float faceOneRightBound = 256.0f / 1024.0f;
        float faceTwoLeftBound = (256.0f + 1.0f) / 1024.0f;
        float faceTwoRightBound = (256.0f * 2.0f) / 1024.0f;
        float faceThreeLeftBound = (256.0f * 2.0f + 1.0f) / 1024.0f;
        float faceThreeRightBound = (256.0f * 3.0f) / 1024.0f;
        float faceFourLeftBound = (256.0f * 3.0f + 1.0f) / 1024.0f;
        float faceFourRightBound = (256.0f * 4.0f) / 1024.0f;
        float faceBoundHalfModifier = (128.0f / 1024.0f);

        float vertices[] =
                {
                        -halfWidth, -halfHeight, halfDepth,
                        halfWidth, -halfHeight, -halfDepth,
                        halfWidth, halfHeight, halfDepth,

                        -halfWidth, -halfHeight, halfDepth,
                        halfWidth, halfHeight, halfDepth,
                        -halfWidth, halfHeight, -halfDepth,

                        -halfWidth, -halfHeight, halfDepth,
                        -halfWidth, halfHeight, -halfDepth,
                        halfWidth, -halfHeight, -halfDepth,

                        halfWidth, halfHeight, halfDepth,
                        halfWidth, -halfHeight, -halfDepth,
                        -halfWidth, halfHeight, -halfDepth
                };

        short indices[] =
                {
                        0, 1, 2,
                        3, 4, 5,
                        6, 7, 8,
                        9, 10, 11
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
                        (faceFourLeftBound + faceBoundHalfModifier), 0.0f
                };

        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        setIndices(indices);

    }
}
