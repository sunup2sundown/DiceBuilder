package edu.okami.m.dicebuilder;

import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class DicePhysics {

    public float rotateX, rotateY, rotateZ, translateX, translateY, translateZ, radius;

    private int numberInOrder, numberOfDice;
    private float xBoundary, yBoundary, zBoundary, zoomOutScalar;
    public boolean isAirborne, isRolling;
    private float[] orientationAngles;

    //Physics members
    public float[] velocityXYZ;
    private float oneMeter;
    private long currentTime, previousTime;
    private final float gravityMPS = 9.8f;
    private final float friction = 0.0005f;
    private float velocityMagnitude = 0.0f;

    private boolean firstUpdate;

    public float theta;


    public DicePhysics(int numberInOrder, int numberOfDice, float radius) {

        this.firstUpdate = true;

        this.theta = 0.0f;

        this.numberInOrder = numberInOrder;
        this.numberOfDice = numberOfDice;
        this.radius = radius;

        this.isAirborne = false;
        this.isRolling = false;

        orientationAngles = new float[3];
        velocityXYZ = new float[3];

        velocityXYZ[0] = 0.0f;
        velocityXYZ[1] = 0.0f;
        velocityXYZ[2] = 0.0f;

        zoomOutScalar = 12.0f;
        xBoundary = (0.25f * zoomOutScalar); //- radius;
        yBoundary = (.40f * zoomOutScalar); //- radius;
        zBoundary = - (zoomOutScalar);

        oneMeter = yBoundary * 10;

        if (numberOfDice == 1) {
            translateX = 0.0f;
            translateY = 0.0f;
            translateZ = zBoundary;
        }
        else if (numberOfDice == 2) {
            if (numberInOrder == 1) {
                translateX = 1.0f;
                translateY = 0.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 2) {
                translateX = -1.0f;
                translateY = 0.0f;
                translateZ = zBoundary;
            }
        }
        else if (numberOfDice == 3) {
            if (numberInOrder == 1) {
                translateX = 1.0f;
                translateY = -1.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 2) {
                translateX = -1.0f;
                translateY = -1.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 3) {
                translateX = 0.0f;
                translateY = 1.0f;
                translateZ = zBoundary;
            }
        }
        else if (numberOfDice == 4) {
            if (numberInOrder == 1) {
                translateX = 1.0f;
                translateY = -1.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 2) {
                translateX = -1.0f;
                translateY = -1.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 3) {
                translateX = -1.0f;
                translateY = 1.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 4) {
                translateX = 1.0f;
                translateY = 1.0f;
                translateZ = zBoundary;
            }
        }
        else if (numberOfDice == 5) {
            if (numberInOrder == 1) {
                translateX = 1.0f;
                translateY = -1.5f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 2) {
                translateX = -1.0f;
                translateY = -1.5f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 3) {
                translateX = 0.0f;
                translateY = 0.0f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 4) {
                translateX = -1.0f;
                translateY = 1.5f;
                translateZ = zBoundary;
            }
            else if (numberInOrder == 5) {
                translateX = 1.0f;
                translateY = 1.5f;
                translateZ = zBoundary;
            }
        }

    }

    public void updateOrientation(float[] orientationAngles) {

        this.orientationAngles = orientationAngles;

    }

    public void collisionEvent(float crashingVelocity[], float crashingLocation[]) {

        float moveRadius = 0;

        if (translateX > crashingLocation[0]) {moveRadius = radius;}
        if (translateX < crashingLocation[0]) {moveRadius = -radius;}
        translateX += ((crashingLocation[0] - translateX) / 2) + moveRadius;

        if (translateY > crashingLocation[1]) {moveRadius = radius;}
        if (translateY < crashingLocation[1]) {moveRadius = -radius;}
        translateY += ((crashingLocation[1] - translateY) / 2) + moveRadius;

        /**
        translateY += (translateY - crashingLocation[1]) / 2;
        translateZ += (translateZ - crashingLocation[2]) / 2;
         **/

        if (translateX + radius > xBoundary) {translateX = xBoundary - radius;}
        if (translateX - radius < -xBoundary) {translateX = -xBoundary + radius;}
        if (translateY + radius > yBoundary) {translateY = yBoundary - radius;}
        if (translateY - radius < -yBoundary) {translateY = -yBoundary + radius;}

        velocityXYZ[0] = Float.valueOf(crashingVelocity[0]) * 0.8f;
        velocityXYZ[1] = Float.valueOf(crashingVelocity[1]) * 0.8f;
        velocityXYZ[2] = Float.valueOf(crashingVelocity[2]) * 0.8f;

    }

    public void updateLocation() {

        previousTime = Long.valueOf(currentTime);
        currentTime = Long.valueOf(System.currentTimeMillis());

        if ((translateX >= (xBoundary - radius)) | (translateX <= (radius - xBoundary))) {

            float[] wallHitVelocity = {- Float.valueOf(velocityXYZ[0]), velocityXYZ[1], velocityXYZ[2]};
            float[] myTranslate = {translateX, translateY, translateZ};
            collisionEvent(wallHitVelocity, myTranslate);

        }
        else if ((translateY >= (yBoundary - radius)) | (translateY <= (radius - yBoundary))) {

            float[] wallHitVelocity = {velocityXYZ[0], - Float.valueOf(velocityXYZ[1]), velocityXYZ[2]};
            float[] myTranslate = {translateX, translateY, translateZ};
            collisionEvent(wallHitVelocity, myTranslate);

        }

        if (!firstUpdate && !isAirborne) { //Quick fix to prevent initial Z-fighting
            velocityXYZ[0] += velocityDueToGravity(orientationAngles[2]);
            velocityXYZ[1] += velocityDueToGravity(orientationAngles[1]);

            //Friction
            if ((velocityXYZ[0] > 0) && ((velocityXYZ[0] - friction) > 0)) {
                velocityXYZ[0] -= friction;
            }
            else if ((velocityXYZ[0] < 0) && ((velocityXYZ[0] + friction) < 0)) {
                velocityXYZ[0] += friction;
            }
            else {
                velocityXYZ[0] = 0;
            }

            if ((velocityXYZ[1] > 0) && ((velocityXYZ[1] - friction) > 0)) {
                velocityXYZ[1] -= friction;
            }
            else if ((velocityXYZ[1] < 0) && ((velocityXYZ[1] + friction) < 0)) {
                velocityXYZ[1] += friction;
            }
            else {
                velocityXYZ[1] = 0;
            }

        }
        else if (!firstUpdate && isAirborne) {

            translateZ += velocityXYZ[2];
            float timeElapsed = (currentTime - previousTime) * .001f;
            velocityXYZ[2] -= timeElapsed;

            if (translateZ < zBoundary) {

                translateZ = Float.valueOf(zBoundary);
                isAirborne = false;

            }

        }
        else {
            firstUpdate = false;
        }

        translateX += velocityXYZ[0];
        translateY += velocityXYZ[1];

        if (translateX + radius > xBoundary) {translateX = xBoundary - radius;}
        if (translateX - radius < -xBoundary) {translateX = -xBoundary + radius;}
        if (translateY + radius > yBoundary) {translateY = yBoundary - radius;}
        if (translateY - radius < -yBoundary) {translateY = -yBoundary + radius;}

        velocityMagnitude = (float) Math.sqrt((velocityXYZ[0] * velocityXYZ[0]) + (velocityXYZ[1] * velocityXYZ[1]));

        if (velocityMagnitude > .05) {
            isRolling = true;
            theta += 100.0f * velocityMagnitude;
        }
        else {
            isRolling = false;
        }

    }

    public float[] getVelocity() {

        return velocityXYZ;

    }

    public float velocityDueToGravity(float slope) {

        float timeElapsed = (currentTime - previousTime) * .001f;

        return gravityMPS * slope * timeElapsed / oneMeter;

    }

    public float zHeading() {

        //Deprecated... but I can't hit backspace ;_;

        double currentZ = (double) Float.valueOf(rotateZ);
        currentZ = Math.toRadians(Double.valueOf(currentZ));

        float[] normal = {Float.valueOf(velocityXYZ[0]), Float.valueOf(velocityXYZ[1])};
        float magnitude = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        float[] unit = {(normal[0] / magnitude), (normal[1] / magnitude)};

        double[][] ruv = { //Rotated Unit Vectors

                {Math.cos(currentZ), Math.sin(currentZ)},
                {-Math.cos(currentZ), -Math.sin(currentZ)},
                {-Math.sin(currentZ), Math.cos(currentZ)},
                {Math.sin(currentZ), -Math.cos(currentZ)}

        };
        double[] signedAngle = {

                Math.toDegrees(Math.atan2( (ruv[0][0] * unit[1] - ruv[0][1] * unit[0]),
                        (ruv[0][0] * unit[0] + ruv[0][1] * unit[1]) )),

                Math.toDegrees(Math.atan2( (ruv[1][0] * unit[1] - ruv[1][1] * unit[0]),
                        (ruv[1][0] * unit[0] + ruv[1][1] * unit[1]) )),

                Math.toDegrees(Math.atan2( (ruv[2][0] * unit[1] - ruv[2][1] * unit[0]),
                        (ruv[2][0] * unit[0] + ruv[2][1] * unit[1]) )),

                Math.toDegrees(Math.atan2( (ruv[3][0] * unit[1] - ruv[3][1] * unit[0]),
                        (ruv[3][0] * unit[0] + ruv[3][1] * unit[1]) )),

        };

        double minValue = signedAngle[0];
        for (int i = 1; i < signedAngle.length; i++) {
            if (Math.abs(signedAngle[i]) < Math.abs(minValue))
                minValue = signedAngle[i];
        }

        if (currentZ == 0) {


        }

        return (float) -minValue;

    }

    public void launchDie (float shakeAcceleration) {

        if (!isAirborne) {

            isAirborne = true;
            float timeElapsed = (currentTime - previousTime) * .001f;
            int rando = 0;
            float random = 0;

            if (shakeAcceleration < 1) {shakeAcceleration = 1;}
            if (shakeAcceleration > 5) {shakeAcceleration = 5;}

            velocityXYZ[2] = shakeAcceleration * timeElapsed * 5;

            rando = (int) (Math.random() * 19) - 10;
            random = 1.0f + (rando * 0.1f);
            velocityXYZ[0] += ((velocityDueToGravity(orientationAngles[2]) + .05 ) * shakeAcceleration) * random;

            rando = (int) (Math.random() * 9);
            random = 1.0f + (rando * 0.1f);
            velocityXYZ[1] += ((velocityDueToGravity(orientationAngles[1]) + .05 )* shakeAcceleration) * random;

        }

    }

}
