package edu.okami.m.dicebuilder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import edu.okami.m.dicebuilder.Adapters.GridAdapter;
import edu.okami.m.dicebuilder.Dialogs.CreateDiceboxDialog;
import edu.okami.m.dicebuilder.Dialogs.NoDiceboxDialog;
import edu.okami.m.dicebuilder.Objects.GridItem;

/**
 * Created by M on 4/22/2017.
 */

public class DashboardActivity extends AppCompatActivity
        implements NoDiceboxDialog.NoDiceboxDialogListener, CreateDiceboxDialog.CreateDiceboxDialogListener{
    private final String TAG = "DashboardActivity";

    private File userDirectory;
    private final String HOME = "home";
    private String userId;

    //Firebase Instances
    StorageReference mStorageReference;
    FirebaseDatabase database;
    DatabaseReference urlReference;

    //Layout Components
    GridView gridView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        userDirectory = new File(getFilesDir(), userId);
        userDirectory = getDir(userId, Context.MODE_PRIVATE);

        if(!userDirectory.exists()){
            userDirectory.mkdirs();
        }

        /**
         * Firebase Instantiation
         */
        database = FirebaseDatabase.getInstance();
        urlReference = database.getReference("URL");

        gridView = (GridView)findViewById(R.id.dashboard_gridview);

        ArrayList<String> filesArray = getFileNames(userDirectory.listFiles());

        if(filesArray != null){
            if(filesArray.size() > 0){
                gridView.setAdapter(new GridAdapter(this, R.layout.gridview_layout, populateGridView()));
            } else{
                NoDiceboxDialog register = new NoDiceboxDialog();
                register.show(getSupportFragmentManager(), "NoDiceboxDialog");
            }
        } else {
            NoDiceboxDialog register = new NoDiceboxDialog();
            register.show(getSupportFragmentManager(), "NoDiceboxDialog");
        }



        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                GridItem item = (GridItem)parent.getItemAtPosition(pos);
                //TODO: Send intent to DiceBoxActivity
                Intent i = new Intent(getApplicationContext(), DiceBoxActivity.class);
                //TODO: Pass name for next activity to retrieve image from Firebase storage
                i.putExtra("BoxName", item.getTitle());
                i.putExtra("UserID", userId);
                startActivity(i);
            }
        });

        button = (Button)findViewById(R.id.button_dashboard_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDiceboxDialog register = new CreateDiceboxDialog();
                register.show(getSupportFragmentManager(), "CreateDiceboxDialog");
            }
        });
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

    /*
    * No Dice Box Dialog Implementation
    *
     */
    @Override
    public void onNoDiceboxPositiveClick(DialogFragment dialog){
        dialog.getDialog().cancel();
        CreateDiceboxDialog register = new CreateDiceboxDialog();
        register.show(getSupportFragmentManager(), "CreateDiceboxDialog");
    }

    @Override
    public void onNoDiceboxNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    /*
    * Create A Dice Box Dialog Implementation
    *
     */
    @Override
    public void onCreateDiceboxPositiveClick(DialogFragment dialog){
        String boxName = ((EditText)dialog.getDialog().findViewById(R.id.createdicebox_dialog_boxname)).getText().toString();

        File directory = new File(userDirectory, boxName);
        directory.mkdir();

        gridView.setAdapter(new GridAdapter(this, R.layout.gridview_layout, populateGridView()));

        /*
        Intent i = new Intent(getApplicationContext(), DiceBoxActivity.class);
        i.putExtra("BoxName", boxName);
        startActivity(i);
        */
    }

    @Override
    public void onCreateDiceboxNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
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

    private ArrayList<GridItem> populateGridView(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bb);
        ArrayList<GridItem> arrayOfGridItems = new ArrayList<GridItem>();
        ArrayList<String> arrayList = getFileNames(userDirectory.listFiles());

           for(int i = 0; i < arrayList.size(); i++){
               GridItem tempGI = new GridItem(bitmap, arrayList.get(i));
               arrayOfGridItems.add(tempGI);
           }

        return arrayOfGridItems;
    }

    private void uploadNewURL(Uri imageURL){
        urlReference.child("url").setValue(imageURL);
    }

    private ArrayList<String> getFileNames(File[] files){
        ArrayList<String> arrayListFiles = new ArrayList<String>();

        if(files != null){
            if(files.length == 0){
                return null;
            } else{
                for(int i = 0; i < files.length; i++){
                    arrayListFiles.add(files[i].getName());
                }
            }
        } else {
            return null;
        }

        return arrayListFiles;
    }


}
