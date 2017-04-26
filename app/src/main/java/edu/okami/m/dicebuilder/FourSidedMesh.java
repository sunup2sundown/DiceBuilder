package edu.okami.m.dicebuilder;

public class FourSidedMesh extends DiceMesh {

    private float width, height, depth;

    public FourSidedMesh(float width, float height, float depth) {

        this.width = width;
        this.height = height;
        this.depth = depth;

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

    @Override
    public float getRadius() {
        return (width + height + depth) / 6;
    }

    @Override
    public float[] getFlatteningEuelerAngles () {

        float[] flatteningEulerAngles = new float[3];
        float root3o3 = (float) (Math.sqrt(3) / 3);
        float[][] ro = {

                {root3o3, -root3o3, root3o3}, {-root3o3, root3o3, root3o3},
                {-root3o3, -root3o3, -root3o3}, {root3o3, root3o3, -root3o3}

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
                {dot(Ro2w[0], ro[3]), dot(Ro2w[1], ro[3]), dot(Ro2w[2], ro[3])}

        };

        float[] down = {0, 0, -1};

        float[] signedAngles = {

                (float) (Math.acos(dot(nw[0], down))),
                (float) (Math.acos(dot(nw[1], down))),
                (float) (Math.acos(dot(nw[2], down))),
                (float) (Math.acos(dot(nw[3], down)))

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
