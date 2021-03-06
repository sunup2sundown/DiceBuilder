package edu.okami.m.dicebuilder;


import android.content.ContextWrapper;
import android.content.Intent;
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

    private final String TAG = "TextureActivity";

    TextureCropFragment cropFragment;
    TextureMenuFragment menuFragment;
    TextureShowDieFragment showDieFragment;

    String boxName = "default";
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);

        try{
            //boxName = getIntent().getStringExtra("box_name");
            boxName = getIntent().getStringExtra("BoxName");
            userId = getIntent().getStringExtra("UserID");
            Log.d("box name", getIntent().getStringExtra("boxName"));
        } catch(Exception e){
            e.printStackTrace();
            Log.d("Box name passed", "no");
        }

        Log.d(TAG, userId);

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
        File directory = cw.getDir(userId, getApplicationContext().MODE_PRIVATE);

        File myPath=new File(directory, boxName);
        File dicePath = new File(myPath, diceName+".png");

        Log.d("dicePath", dicePath.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dicePath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //saveToFirebase(boxName, image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        showDieFragment.setDiePath(dicePath.getAbsolutePath());

        return dicePath.getAbsolutePath();
    }

    private void saveToFirebase(String name, Bitmap image){
        //TODO: Get Image and Name as argument
        //TODO: Open up storage instance and send to storage
        //TODO: Get download link for image in storage
        //TODO: Use Userid to get database reference to user's images
        //TODO: Store the name and download link in user's images in database

    }

    public void backToDiceBox(){
        Intent i = new Intent(this, DiceBoxActivity.class);
        i.putExtra("BoxName", boxName);
        i.putExtra("UserID", userId);
        startActivity(i);
    }

    private void saveToInternalStorage(Bitmap bitmapImage, File dicePath){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dicePath);
            Log.d("Mike Path", dicePath.getAbsolutePath());
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
    }
}





