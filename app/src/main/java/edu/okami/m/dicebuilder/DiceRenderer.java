package edu.okami.m.dicebuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import javax.microedition.khronos.opengles.GL10;

public class DiceRenderer extends GLRenderer {

    private Context context;

    FourSidedMesh four = new FourSidedMesh(1, 1, 1);
    SixSidedMesh six = new SixSidedMesh(1);
    EightSidedMesh eight = new EightSidedMesh(1);
    TenSidedMesh ten = new TenSidedMesh(0.75f);
    TwelveSidedMesh twelve = new TwelveSidedMesh(0.75f);
    TwentySidedMesh twenty = new TwentySidedMesh(0.5f);
    Bitmap bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6;
    float rotateX = 0;
    float rotateY = 0;

    public DiceRenderer(Context c) {
        context = c;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.four_sided_texture);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.full_die_texture);
        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.eight_sided_texture);
        bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ten_sided_texture);
        bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.twelve_sided_texture);
        bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.twenty_sided_die);
        four.loadBitmap(bitmap);
        six.loadBitmap(bitmap2);
        eight.loadBitmap(bitmap3);
        ten.loadBitmap(bitmap4);
        twelve.loadBitmap(bitmap5);
        twenty.loadBitmap(bitmap6);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        //rotateX++;
        rotateY++;

        four.rotateX = -rotateX;
        four.rotateY = -rotateY;
        six.rotateX = rotateX;
        six.rotateY = rotateY;
        eight.rotateX = -rotateX;
        eight.rotateY = -rotateY;
        ten.rotateX = rotateX;
        ten.rotateY = rotateY;
        twelve.rotateX = -rotateX;
        twelve.rotateY = -rotateY;
        twenty.rotateX = rotateX;
        twenty.rotateY = rotateY;

        four.translateX = -1;
        four.translateY = 2;
        four.translateZ = -10;
        six.translateX = 1;
        six.translateY = 2;
        six.translateZ = -10;
        eight.translateX = -1;
        eight.translateY = 0;
        eight.translateZ = -10;
        ten.translateX = 1;
        ten.translateY = 0;
        ten.translateZ = -10;
        twelve.translateX = -1;
        twelve.translateY = -2;
        twelve.translateZ = -10;
        twenty.translateX = 1;
        twenty.translateY = -2;
        twenty.translateZ = -10;

        four.draw(gl);
        six.draw(gl);
        eight.draw(gl);
        ten.draw(gl);
        twelve.draw(gl);
        twenty.draw(gl);

    }

}
