package edu.okami.m.dicebuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomDie {

    private String path;
    private Context context;
    private String[] splitPath;
    private String dieName;
    private String diceBoxName;
    private int numberOfSides;

    public CustomDie(String path, Context context) {

        this.path = path;
        this.context = context;

        ///data/user/0/edu.okami.m.dicebuilder/app_userid/Test_Dice_Box/Six_Sided_Die.png

        String[] splitPath = path.split("/");

        this.diceBoxName = splitPath[splitPath.length - 2];
        this.dieName = splitPath[splitPath.length - 1];

        splitPath = diceBoxName.split("_");
        diceBoxName = "";
        for (int i = 0; i < splitPath.length; i++) {
            if (i > 0) {diceBoxName = diceBoxName + " ";}
            diceBoxName = diceBoxName + splitPath[i];
        }

        splitPath = dieName.split("\\.");
        dieName = splitPath[0];
        splitPath = dieName.split("_");
        dieName = "";
        for (int i = 0; i < splitPath.length; i++) {
            if (i > 0) {dieName = dieName + " ";}
            dieName = dieName + splitPath[i];
        }


        Bitmap bitmap = loadImageFromStorage();
        this.numberOfSides = (int) (bitmap.getWidth() / bitmap.getHeight());
        Log.d("Sides", Integer.toString(numberOfSides));
        bitmap.recycle();

    }

    public String getPath() {
        return this.path;
    }

    public String getDieName() {
        return this.dieName;
    }

    public String getDiceBoxName() {
        return this.diceBoxName;
    }

    public int getNumberOfSides() {
        return this.numberOfSides;
    }

    private Bitmap loadImageFromStorage()
    {

        try {
            File file = new File(this.path);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }

}
