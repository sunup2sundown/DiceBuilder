package edu.okami.m.dicebuilder.Objects;

import android.graphics.Bitmap;

/**
 * Created by M on 4/26/2017.
 */

public class DiceItem {
    private Bitmap image;
    private String title;

    public DiceItem(Bitmap image, String title){
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}