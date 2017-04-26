package edu.okami.m.dicebuilder.Objects;

/**
 * Created by M on 4/26/2017.
 */

public class Dicebox {
    private String name;
    private String link;

    public Dicebox(String name, String link){
        this.name = name;
        this.link = link;
    }

    public String getName(){
        return name;
    }

    public String getLink(){
        return link;
    }
}
