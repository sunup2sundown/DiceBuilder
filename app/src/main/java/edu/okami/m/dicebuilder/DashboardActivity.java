package edu.okami.m.dicebuilder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

import edu.okami.m.dicebuilder.Adapters.ImageAdapter;

/**
 * Created by M on 4/22/2017.
 */

public class DashboardActivity extends AppCompatActivity{
    private final String TAG = "DashboardActivity";

    //Firebase Instances
    StorageReference mStorageReference;
    FirebaseDatabase database;
    DatabaseReference urlReference;

    //LAyout Components
    GridView gridView;
    Button button;

    /**
     * Dummy Data
     *
     */
    String[] gridViewStrings = {"Dnd", "Warhammer", "Call of Cthulhu"};
    int[] gridViewImages = {R.drawable.bb, R.drawable.boba_fett, R.drawable.dark_souls};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /**
         * Firebase Instantiation
         */
        database = FirebaseDatabase.getInstance();
        urlReference = database.getReference("URL");

        gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this, gridViewStrings, gridViewImages));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                //TODO: Send intent to DiceBoxActivity
                Intent i = new Intent(getApplicationContext(), DiceBoxActivity.class);
                //TODO: Pass name for next activity to retrieve image from Firebase storage
                i.putExtra("name", "");
                startActivity(i);
            }
        });

        button = (Button)findViewById(R.id.button_dashboard_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Open up dice box creation dialog
                //TODO: Store in FBStorage and also send download URL to Realtime Database
            }
        });

/*
        Drawable drawable = getResources().getDrawable(R.drawable.boba_fett);
        Bitmap bm = drawableToBitmap(drawable);
        uploadFile(bm);
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //If there's a download in progress, save the reference so you can query it later
        if(mStorageReference != null){
            outState.putString("reference", mStorageReference.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        //If there was a download in progress, get its reference and create a new StorageReference
        final String stringRef = savedInstanceState.getString("reference");
        if(stringRef == null){
            return;
        }
        mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);
        //Find all download tasks under this storage reference
        List<FileDownloadTask> tasks = mStorageReference.getActiveDownloadTasks();
        if(tasks.size() > 0){
            //Get the task monitoring the download
            FileDownloadTask task = tasks.get(0);
            //Add new listeners to the task using an activity scope
            task.addOnSuccessListener(this, new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Hand Success
                }
            });
        }

    }

    private void uploadFile(Bitmap bitmap){
        //Firebase instantiation
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //References .getReference, .getChild, .getRoot, .getParent
        //Create reference to firebase storage
        StorageReference storageReference = storage.getReference();
        //create reference to child folder
        StorageReference diceboxRef = storageReference.child("dicebox");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, os);
        byte[] data = os.toByteArray();

        UploadTask uploadTask = diceboxRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //TODO: Handle failed outputstream to firebase
                Toast.makeText(getApplicationContext(), "Dicebox storage was unsuccessful.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata(); will contain file metadata such as size, content-type and download url
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                Toast.makeText(getApplicationContext(), "Dicebox stored on server!", Toast.LENGTH_SHORT).show();
                uploadNewURL(downloadUrl);
            }
        });
    }

    private void downloadFile(StorageReference storageReference, String name){
        final long ONE_MEAGABYTE = 1024 * 1024;
        //TODO: NEed to download file from URLs stored in database and then populate gridview

        StorageReference diceBoxReference = storageReference.child("dicebox/" + name);
        diceBoxReference.getBytes(ONE_MEAGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Data for image is returned
                //TODO: Do what you want with image
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //TODO: Handle any errors
            }
        });
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void populateGridView(){
        //

    }

    private void uploadDiceBoxToStorage(){

    }

    private void uploadNewURL(Uri imageURL){
        urlReference.child("url").setValue(imageURL);
    }

    private void downloadAllURL(){

    }
}
