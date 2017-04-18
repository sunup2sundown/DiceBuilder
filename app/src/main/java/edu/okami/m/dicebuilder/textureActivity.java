package edu.okami.m.dicebuilder;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lyft.android.scissors.CropView;

public class textureActivity extends AppCompatActivity {


    Bitmap[] images = new Bitmap[6];
    int imageCount = 0;
    /* final String image1 = "http://read.pudn.com/downloads109/sourcecode/multimedia/vfw/452281/movie%20rgbEdge/res/bitmap__.jpg";
    final String image2 = "https://www.easycalculation.com/area/images/big-square.gif";
    final String image3 = "https://yt3.ggpht.com/-rbcC6Hd9Cck/AAAAAAAAAAI/AAAAAAAAAAA/NT0jyoaJFwA/s900-c-k-no-mo-rj-c0xffffff/photo.jpg";
    final String image4 = "http://www.politicalmetaphors.com/wp-content/uploads/2015/04/blog-shapes-square-windows.jpg";
    final String image5 = "https://img.clipartfest.com/3d0bca509c5aab8e12748ce756dc6645_download-clipart-square-eyes_625-626.jpeg";
    final String image6 = "https://www.jaapsch.net/puzzles/images/square2.jpg"; */
    CropView cv;
    boolean isCropping = false;
    TextView countTextView;

    /*BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("intent received", "yes");
            Bitmap merged = merge(images);
            cv.setImageBitmap(merged);
            MediaStore.Images.Media.insertImage(getContentResolver(), merged, "Merged Image" , "Desc");
        }
    };
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);

        countTextView = (TextView)findViewById(R.id.countView);
        cv = (CropView)findViewById(R.id.crop_view);

        /*IntentFilter intf = new IntentFilter("imagedone2");
        registerReceiver(br,intf);
        */

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);


        Button cropButton = (Button)findViewById(R.id.button);
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if less than 5 images selected just increment
                if(imageCount < 5) {
                    Bitmap croppedImage;
                    croppedImage = cv.crop();
                    Bitmap resizedImage = Bitmap.createScaledBitmap(croppedImage, 256, 256, false);
                    images[imageCount] = resizedImage;
                    imageCount++;
                    countTextView.setText(imageCount + "/6 images selected");
                    //MediaStore.Images.Media.insertImage(getContentResolver(), resizedImage, "Test" , "Made from DiceBuilder");
                    Log.d("Cropped?", "Yes");

                    isCropping = false;
                }
                else{
                    //on the 6th image being selected combine and save it to gallery
                    Bitmap croppedImage;
                    croppedImage = cv.crop();
                    Bitmap resizedImage = Bitmap.createScaledBitmap(croppedImage, 256, 256, false);
                    images[imageCount] = resizedImage;
                    imageCount++;

                    Log.d("Cropped?", "Final crop");
                    Bitmap merged = merge(images);
                    MediaStore.Images.Media.insertImage(getContentResolver(), merged, "Texture" , "Made from DiceBuilder");
                    countTextView.setText("Merged image saved to gallery.");

                }
            }
        });

        Button selectButton = (Button)findViewById(R.id.button2);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCropping){
                    Toast.makeText(textureActivity.this, "You must finish cropping current image", Toast.LENGTH_SHORT).show();
                } else {
                    Intent photoIntent = new Intent();

                    photoIntent.setType("image/*");
                    photoIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(photoIntent, "Select Picture"), 8);
                }
            }
        });

        Button goBack = (Button)findViewById(R.id.button3);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goBackIntent);
            }
        });


/*
        Thread t = new Thread(){
            @Override
            public void run() {
                try {

                    URL url = new URL(image1);
                    images[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    images[0] = Bitmap.createScaledBitmap(images[0], 256, 256, false);

                    URL url2 = new URL(image2);
                    images[1] = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                    images[1] = Bitmap.createScaledBitmap(images[1], 256, 256, false);

                    URL url3 = new URL(image3);
                    images[2] = BitmapFactory.decodeStream(url3.openConnection().getInputStream());
                    images[2] = Bitmap.createScaledBitmap(images[2], 256, 256, false);

                    URL url4 = new URL(image4);
                    images[3] = BitmapFactory.decodeStream(url4.openConnection().getInputStream());
                    images[3] = Bitmap.createScaledBitmap(images[3], 256, 256, false);

                    URL url5 = new URL(image5);
                    images[4] = BitmapFactory.decodeStream(url5.openConnection().getInputStream());
                    images[4] = Bitmap.createScaledBitmap(images[4], 256, 256, false);

                    URL url6 = new URL(image6);
                    images[5] = BitmapFactory.decodeStream(url6.openConnection().getInputStream());
                    images[5] = Bitmap.createScaledBitmap(images[5], 256, 256, false);


                    Intent it = new Intent();
                    it.setAction("imagedone2");
                    sendBroadcast(it);
                    Log.d("image dled", "yes");
                } catch(IOException e) {
                    System.out.println(e);
                }
            }
        };
        t.start;
*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8 && resultCode == RESULT_OK && data != null){

            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                cv.setImageBitmap(bitmap);
                isCropping = true;
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }*/

    public Bitmap merge(Bitmap[] images){
        Bitmap retval = Bitmap.createBitmap(images[0].getWidth()*6, images[0].getHeight(), images[0].getConfig());
        Canvas canvas = new Canvas(retval);
        Paint paint = new Paint();
        for (int i = 0; i < images.length; i++) {
            canvas.drawBitmap(images[i], images[i].getWidth() * (i % 6), images[i].getHeight()*(i/6), paint);
        }
        return retval;
    }
    }


