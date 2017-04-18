package edu.okami.m.dicebuilder;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiceRollActivity extends AppCompatActivity {

    CustomDie customDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        //Test Code
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.six_sided_texture);
        String internalPath = saveToInternalStorage(bitmap);

        Log.d("Image path", internalPath);

        customDie = new CustomDie(internalPath, getApplicationContext());

    }

    @Override
    public void onAttachedToWindow() {

        String toasty = "You made a " + Integer.toString(customDie.getNumberOfSides()) + "-sided die called " +
                customDie.getDieName() + " in the dice box called " + customDie.getDiceBoxName() + ".";

        Toast.makeText(this, toasty, Toast.LENGTH_LONG).show();
        Log.d("Object test", toasty);

    }

    //Code to place a test image in internal storage and return the path as a string
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Test_Dice_Box", getApplicationContext().MODE_PRIVATE);
        // Create imageDir
        File myPath=new File(directory,"Six_Sided_Die.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myPath.getAbsolutePath();
    }

}
