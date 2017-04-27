package edu.okami.m.dicebuilder;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DiceRollActivity extends AppCompatActivity implements SensorEventListener, DiceRenderer.Rotatable {

    private CustomDie customDie;

    private SensorManager sensorManager;
    private Sensor accelerometer, magneticField;
    private float[] accelerometerReading, magnetometerReading, orientationAngles, rotationMatrix;

    private DiceRenderer diceRenderer;
    private CustomDie[] customDice;
    private GLSurfaceView glSurfaceView;

    private DisplayMetrics displayMetrics;
    private int screenWidth, screenHeight;

    private float currentZ, lastZ, shakeAcceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        accelerometerReading = new float[3];
        magnetometerReading = new float[3];
        orientationAngles = new float[3];
        rotationMatrix = new float[9];

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        currentZ = 9.8f;

        /**Test Code
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.six_sided_texture);
        String internalPath = saveToInternalStorage(bitmap);

        customDie = new CustomDie(internalPath, getApplicationContext());

        customDice = new CustomDie[5];
        customDice[0] = customDie;
        customDice[1] = customDie;
        customDice[2] = customDie;
        customDice[3] = customDie;
        customDice[4] = customDie;
         **/

        ArrayList<String> dieArrayList;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            dieArrayList = bundle.getStringArrayList("PATHS");

            customDice = new CustomDie[dieArrayList.size()];

            for (int i = 0; i < dieArrayList.size(); i++) {
                customDice[i] = new CustomDie(dieArrayList.get(i), getApplicationContext());
            }

        }

        diceRenderer = new DiceRenderer(customDice, this, screenWidth, screenHeight);

        //Make the Activity go full screen with no title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(diceRenderer);
        setContentView(glSurfaceView);

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            System.arraycopy(sensorEvent.values, 0, accelerometerReading, 0, accelerometerReading.length);

        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            System.arraycopy(sensorEvent.values, 0, magnetometerReading, 0, magnetometerReading.length);

        }

        lastZ = Float.valueOf(currentZ);
        currentZ = Float.valueOf(accelerometerReading[2]);
        shakeAcceleration = currentZ - lastZ;


        if (shakeAcceleration >= 0.5) {

            diceRenderer.getShake(shakeAcceleration);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {

        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onPause() {

        super.onPause();

        sensorManager.unregisterListener(this);

    }

    @Override
    public float[] getOrientationAngles() {

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        return orientationAngles;

    }
}
