package edu.okami.m.dicebuilder.Objects;

import android.graphics.Bitmap;

import edu.okami.m.dicebuilder.CustomDie;

/**
 * Created by M on 4/26/2017.
 */

public class DiceItem {
    private Bitmap image;
    private String title;
    private String filePath;

    public DiceItem(Bitmap image, String title, String filePath){
        super();
        this.image = image;
        this.title = title;
        this.filePath = filePath;
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

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
}