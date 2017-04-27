package edu.okami.m.dicebuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Michael on 4/27/17.
 */

public class ResultRenderer extends GLRenderer {

    private Context context;
    private CustomDie customDie;
    private DiceMesh diceMesh;
    private File file;
    private Bitmap bitmap;

    public ResultRenderer(Context c, CustomDie customDie) {

        this.context = c;
        this.customDie = customDie;

        if (customDie.getNumberOfSides() == 4) {
            diceMesh = new FourSidedMesh(1.0f, 1.0f, 1.0f);
        } else if (customDie.getNumberOfSides() == 6) {
            diceMesh = new SixSidedMesh(1.0f);
        } else if (customDie.getNumberOfSides() == 8) {
            diceMesh = new EightSidedMesh(1.0f);
        } else if (customDie.getNumberOfSides() == 10) {
            diceMesh = new TenSidedMesh(1.0f);
        } else if (customDie.getNumberOfSides() == 12) {
            diceMesh = new TwelveSidedMesh(1.0f);
        } else if (customDie.getNumberOfSides() == 20) {
            diceMesh = new TwentySidedMesh(0.50f);
        } else {
            // TODO Dice size exception
        }
        file = new File(customDie.getPath());
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        diceMesh.loadBitmap(bitmap);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        diceMesh.translateZ = -6.0f;

        if (diceMesh.rotateY < 360) {
            diceMesh.rotateY+= 2;
        }
        else if (diceMesh.rotateX < 360) {
            diceMesh.rotateX+= 2;
        }
        else {
            diceMesh.rotateY = 2;
            diceMesh.rotateX = 0;
        }

        diceMesh.draw(gl);

    }

}
