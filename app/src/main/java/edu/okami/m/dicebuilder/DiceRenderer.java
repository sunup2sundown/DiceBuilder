package edu.okami.m.dicebuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.Arrays;

import javax.microedition.khronos.opengles.GL10;

public class DiceRenderer extends GLRenderer {

    private Context context;
    private CustomDie customDie[];
    private DiceMesh diceMesh[];
    private DicePhysics dicePhysics[];
    private Bitmap bitmap;
    private File file;
    private int screenWidth, screenHeight;
    private float screenWidthScalar, screenHeightScalar;
    private float[] rotateYZX, targetRotateYZX;

    private boolean firstDraw;

    Rotatable parent;
    private float[] orientationAngles;

    public DiceRenderer(CustomDie customDie[], Context c, int screenWidth, int screenHeight) {

        firstDraw = true;

        this.context = c;
        this.parent = (Rotatable) context;
        this.customDie = customDie;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.screenWidthScalar = (float)(screenWidth) / 1080;
        this.screenHeightScalar = (float)(screenHeight) / 1794;

        this.rotateYZX = new float[3];
        this.targetRotateYZX = new float[3];

        diceMesh = new DiceMesh[this.customDie.length];
        dicePhysics = new DicePhysics[this.customDie.length];

        for (int i = 0; i < this.customDie.length; i++) {

            if (customDie[i].getNumberOfSides() == 4) {
                diceMesh[i] = new FourSidedMesh(1.0f, 1.0f, 1.0f);
            } else if (customDie[i].getNumberOfSides() == 6) {
                diceMesh[i] = new SixSidedMesh(1.0f);
            } else if (customDie[i].getNumberOfSides() == 8) {
                diceMesh[i] = new EightSidedMesh(1.0f);
            } else if (customDie[i].getNumberOfSides() == 10) {
                diceMesh[i] = new TenSidedMesh(1.0f);
            } else if (customDie[i].getNumberOfSides() == 12) {
                diceMesh[i] = new TwelveSidedMesh(1.0f);
            } else if (customDie[i].getNumberOfSides() == 20) {
                diceMesh[i] = new TwentySidedMesh(0.50f);
            } else {
                // TODO Dice size exception
            }
            file = new File(customDie[i].getPath());
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Log.d("Scaling", Integer.toString(bitmap.getWidth()));

            if ((bitmap.getWidth() / 2) == 2560) {

                Log.d("Scaling", "Tried scaling a 10 sided texture.");
                bitmap = Bitmap.createScaledBitmap(bitmap, 1280, 128, false);

            }

            if ((bitmap.getWidth() / 2) == 3072) {

                Log.d("Scaling", "Tried scaling a 10 sided texture.");
                bitmap = Bitmap.createScaledBitmap(bitmap, 1536, 128, false);

            }

            if ((bitmap.getWidth() / 2) == 5120) {

                Log.d("Scaling", "Tried scaling a 10 sided texture.");
                bitmap = Bitmap.createScaledBitmap(bitmap, 1280, 64, false);

            }

            diceMesh[i].loadBitmap(bitmap);
            dicePhysics[i] = new DicePhysics(i + 1, this.customDie.length, diceMesh[i].getRadius());
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        if (firstDraw) {

            for (int i = 0; i < customDie.length; i++) {
                diceMesh[i].draw(gl);
            }

            firstDraw = false;

        }

        orientationAngles = parent.getOrientationAngles();

        for (int i = 0; i < customDie.length; i++) {

            dicePhysics[i].updateOrientation(orientationAngles);
            dicePhysics[i].updateLocation();

        }


        for (int i = 0; i < customDie.length; i++) {

            for (int j = i + 1; j < customDie.length; j++) {

                if (i != j) {

                    if (collisionDetected(dicePhysics[i], dicePhysics[j])) {

                        float[] velocityHolderI = dicePhysics[i].getVelocity();
                        float[] velocityHolderJ = dicePhysics[j].getVelocity();
                        float[] valueHolderI = {Float.valueOf(velocityHolderI[0]),
                                Float.valueOf(velocityHolderI[1]),
                                Float.valueOf(velocityHolderI[2])};
                        float[] valueHolderJ = {Float.valueOf(velocityHolderJ[0]),
                                Float.valueOf(velocityHolderJ[1]),
                                Float.valueOf(velocityHolderJ[2])};

                        float[] translateHolderI = {Float.valueOf(dicePhysics[i].translateX),
                                Float.valueOf(dicePhysics[i].translateY),
                                Float.valueOf(dicePhysics[i].translateZ)};
                        float[] translateHolderJ = {Float.valueOf(dicePhysics[j].translateX),
                                Float.valueOf(dicePhysics[j].translateY),
                                Float.valueOf(dicePhysics[j].translateZ)};
                        dicePhysics[i].collisionEvent(valueHolderJ, translateHolderJ);
                        dicePhysics[j].collisionEvent(valueHolderI, translateHolderI);


                    }

                }

            }

        }

        for (int i = 0; i < customDie.length; i++) {

            if (dicePhysics[i].isRolling) {
                diceMesh[i].theta = dicePhysics[i].theta;
                diceMesh[i].rotateVelocityX = dicePhysics[i].velocityXYZ[0];
                diceMesh[i].rotateVelocityY = dicePhysics[i].velocityXYZ[1];

                diceMesh[i].rotateY = 0;
                diceMesh[i].rotateZ = 0;
                diceMesh[i].rotateX = 0;
            }
            else {

                rotateYZX = diceMesh[i].getEulerAngles();
                targetRotateYZX = diceMesh[i].getFlatteningEuelerAngles();

                for (int j = 0; j < 3; j++) {

                    if (rotateYZX[j] >= 360.0f) {rotateYZX[j] = 0.0f;}
                    if (rotateYZX[j] == -0.0f) {rotateYZX[j] = 0.0f;}
                    if (rotateYZX[j] < 0.0f) {rotateYZX[j] = 360.0f;}

                    float absDifference = (float) Math.abs(rotateYZX[j] - targetRotateYZX[j]);

                    if (rotateYZX[j] < targetRotateYZX[j]) {

                        if (absDifference < 180.0f) {
                            rotateYZX[j] += 1.0f;
                        }
                        else {
                            rotateYZX[j] -= 1.0f;
                        }

                    }
                    else if (rotateYZX[j] > targetRotateYZX[j]) {

                        if (absDifference < 180.0f) {
                            rotateYZX[j] -= 1.0f;
                        }
                        else {
                            rotateYZX[j] += 1.0f;
                        }

                    }

                    diceMesh[i].theta = 0.0f;
                    diceMesh[i].rotateVelocityX = 0.0f;
                    diceMesh[i].rotateVelocityY = 0.0f;

                    diceMesh[i].rotateY = rotateYZX[0];
                    diceMesh[i].rotateZ = rotateYZX[1];
                    diceMesh[i].rotateX = rotateYZX[2];

                }
            }

            diceMesh[i].translateX = dicePhysics[i].translateX * screenWidthScalar;
            diceMesh[i].translateY = dicePhysics[i].translateY * screenHeightScalar;
            diceMesh[i].translateZ = dicePhysics[i].translateZ;

            diceMesh[i].draw(gl);
        }

    }

    public boolean collisionDetected(DicePhysics first, DicePhysics second) {

        float distance = (float) Math.sqrt( ((second.translateX - first.translateX) * (second.translateX - first.translateX))
                + ((second.translateY - first.translateY) * (second.translateY - first.translateY))
                + ((second.translateZ - first.translateZ) * (second.translateZ - first.translateZ)) );

        if (distance <= (first.radius + second.radius))
            return true;
        else
            return false;
    }

    public interface Rotatable {

        public float[] getOrientationAngles();

    }

    public void getShake(float shakeAcceleration) {

        for (int i = 0; i < customDie.length; i++) {

            dicePhysics[i].launchDie(shakeAcceleration);

        }

    }

}
