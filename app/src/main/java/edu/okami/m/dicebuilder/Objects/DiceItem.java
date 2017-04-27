package edu.okami.m.dicebuilder.Objects;

import android.graphics.Bitmap;

import edu.okami.m.dicebuilder.CustomDie;

/**
 * Created by M on 4/26/2017.
 */

public class DiceItem {
    private Bitmap image;
    private String title;
    private CustomDie customDie;

    public DiceItem(Bitmap image, String title, CustomDie customDie){
        super();
        this.image = image;
        this.title = title;
        this.customDie = customDie;
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

    public CustomDie getCustomDie(){
        return customDie;
    }

    public void setCustomDie(CustomDie customDie){
        this.customDie = customDie;
    }
}