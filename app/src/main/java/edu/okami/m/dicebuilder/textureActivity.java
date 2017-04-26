package edu.okami.m.dicebuilder;


import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class textureActivity extends AppCompatActivity implements TextureCropFragment.OnFragmentInteractionListener,
        TextureMenuFragment.OnFragmentInteractionListener, TextureShowDieFragment.OnFragmentInteractionListener {

    TextureCropFragment cropFragment;
    TextureMenuFragment menuFragment;
    TextureShowDieFragment showDieFragment;

    String boxName = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);

        try{
            boxName = getIntent().getStringExtra("box_name");
            Log.d("box name", getIntent().getStringExtra("box_name"));
        } catch(Exception e){
            e.printStackTrace();
            Log.d("Box name passed", "no");
        }


        cropFragment = new TextureCropFragment();
        menuFragment = new TextureMenuFragment();
        showDieFragment = new TextureShowDieFragment();

        getFragmentManager().beginTransaction().add(R.id.textureLayout, menuFragment).commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void beginCropFragment(String diceName, int sides) {
        Log.d("begin crop frag ", "hey");
        getFragmentManager().beginTransaction().remove(menuFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.textureLayout, cropFragment).commit();
        cropFragment.getInfo(diceName, sides);
    }

    @Override
    public void beginShowDieFragment(String diceName){
        getFragmentManager().beginTransaction().remove(cropFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.textureLayout, showDieFragment).commit();
        showDieFragment.setDieName(diceName);

    }

    @Override
    public String saveMergedImage(Bitmap image, String diceName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File directory = cw.getDir(boxName, getApplicationContext().MODE_PRIVATE);

        File myPath = new File(directory, diceName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
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




